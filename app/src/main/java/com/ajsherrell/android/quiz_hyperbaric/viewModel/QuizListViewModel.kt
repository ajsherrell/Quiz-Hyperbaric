/*
 * Copyright 2020 AJ Sherrell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ajsherrell.android.quiz_hyperbaric.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.ajsherrell.android.quiz_hyperbaric.R
import com.ajsherrell.android.quiz_hyperbaric.database.QuizRepository
import com.ajsherrell.android.quiz_hyperbaric.model.Category
import com.ajsherrell.android.quiz_hyperbaric.model.Questions
import com.ajsherrell.android.quiz_hyperbaric.model.Response
import com.ajsherrell.android.quiz_hyperbaric.network.NetworkModule
import com.ajsherrell.android.quiz_hyperbaric.utils.SharedPreferenceHelper
import com.ajsherrell.android.quiz_hyperbaric.utils.getOrEmpty
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class QuizListViewModel(val app: Application) : AndroidViewModel(app) {

    var connManager: ConnectivityManager = app.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    var score: String = ""
    var bank = listOf<Questions>()
    lateinit var currentQuestion: Questions
    var category: Category? = null
    var currentIndex = 0
    var selectedText: String = ""
    var isAnswered: Boolean = false
    lateinit var scoreCategory: String

    //app bar title
    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title
    fun updateActionBarTitle(title: String) = _title.postValue(title)

    //radioButtons
    lateinit var rg: RadioGroup
    var rb: RadioButton? = null
    lateinit var rb1: RadioButton
    lateinit var rb2: RadioButton
    lateinit var rb3: RadioButton
    lateinit var rb4: RadioButton

    var scores = mutableListOf<String>()
    var categories = mutableListOf<String>()

    val sbScore = StringBuilder()
    val sbCat = StringBuilder()

    private val job = Job()

    private val coroutineContext : CoroutineContext get() = job + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    private val repo : QuizRepository = QuizRepository(NetworkModule.quizApi)

    val mutableLoading: MutableLiveData<Int?> = MutableLiveData()

    //profile
    var profileName = ObservableField("")
    var profileTitle = ObservableField("")

    private val sharedPrefs by lazy { SharedPreferenceHelper(app) }

    fun clearSharedPrefs() {
        sharedPrefs.clearHighScores()
    }

    fun saveProfile() {
        sharedPrefs.saveProfile(profileName.getOrEmpty(), profileTitle.getOrEmpty())
    }

    fun saveHighScore() {
        sharedPrefs.saveHighScores(categories, scores)
    }

    fun loadProfile() {
        val profile = sharedPrefs.getProfile()
        profileName.set(profile.name)
        profileTitle.set(profile.title)
    }

    fun hasFullProfile(): Boolean { //todo: do I need these two functions?
        val profile = sharedPrefs.getProfile()
        return profile.name.isNotEmpty() && profile.title.isNotEmpty()
    }

    fun hasFullScores(): Boolean {
        val highScores = sharedPrefs.getHighScores()
        return highScores.categories.isNotEmpty() && highScores.scores.isNotEmpty()
    }

    fun loadHighScores() {
        val highScores = sharedPrefs.getHighScores()
        categories = highScores.categories
        scores = highScores.scores
    }

    fun scorePercentage(score: Int) : Int {
        val percentage = (score.toDouble() / 4) * 100
        return percentage.toInt()
    }

    var errorMessage: String = ""
    val errorClickListener = View.OnClickListener {
    if (isWiFiConnected()) {
        getQuizData()
        mutableLoading.value = View.GONE
    } else {
        Toast.makeText(app.applicationContext, "Please connect to Wi-Fi", Toast.LENGTH_LONG).show()
    }
}

    private val _quizLiveData: MutableLiveData<Response> = MutableLiveData()
    val quizLiveData: LiveData <Response>
        get() = _quizLiveData

    private val _catLiveData: MutableLiveData<List<Category>> = MutableLiveData()
    val catLiveData: LiveData<List<Category>>
        get() = _catLiveData

    fun getQuizData() { //sets up the ListFragment
        try {
            scope.launch {
                onRetrieveDataSuccess()
                val quizData = repo.getQuizData()
                _quizLiveData.value = quizData
                onRetrieveDataFinish()
            }
        } catch (e: IllegalThreadStateException) {
            e.printStackTrace()
            onRetrieveDataError()
            Timber.e("$e !!! ${e.message}")
        } finally {
            onRetrieveDataFinish()
        }
    }

    fun getQData() { //sets up the DetailFragment
        try {
            scope.launch {
                val qData = repo.getQData()
                _catLiveData.value = qData
            }
        } catch (e: IllegalThreadStateException) {
            e.printStackTrace()
            Timber.e("$e")
        }
    }

    private fun cancelRequest() = coroutineContext.cancel()

    private fun onRetrieveDataSuccess() {
        mutableLoading.value = View.VISIBLE
    }

    private fun onRetrieveDataError() {
        mutableLoading.value = View.GONE
        errorMessage = R.string.error_message.toString()
        cancelRequest()
    }

    private fun onRetrieveDataFinish() {
        mutableLoading.value = View.GONE
    }

    override fun onCleared() {
        super.onCleared()
        cancelRequest()
    }

//    fun addScores() {
//        for (i in 0 until categories.size) { //todo: use a mutable map?
//            if (scoreCategory != categories[i]) {
//                sbCat.append(categories[i])
//                sbCat.append("\n")
//                for (j in 0 until scores.size) {
//                    sbScore.append(" ")
//                    sbScore.append(scores[j])
//                    sbScore.append("\n")
//                }
//            }
//        }
//    }

    fun addScores(): MutableMap<String, Int> {
        val mutableMap: MutableMap<String, Int> = mutableMapOf()
        var cat = ""
        var s = 0
        for (i in 0 until categories.size) {
            cat = categories[i]
        }
        for (i in 0 until scores.size) {
            score = scores[i]
            s = score.toInt()
        }
        mutableMap[cat] = s
        return mutableMap
    }

    fun isWiFiConnected(): Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connManager.activeNetwork
            val capabilities = connManager.getNetworkCapabilities(network)
            capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } else {
            return false
        }
    }

}
