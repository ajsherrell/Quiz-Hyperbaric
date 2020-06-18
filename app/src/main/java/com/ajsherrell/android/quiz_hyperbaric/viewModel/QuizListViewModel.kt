package com.ajsherrell.android.quiz_hyperbaric.viewModel

import android.app.Application
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
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
import com.google.gson.Gson
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class QuizListViewModel(val app: Application) : AndroidViewModel(app) {

    var score: String = ""
    var questionBank = listOf<Questions>()
    var category: Category? = null
    var currentIndex = 0
    var answer = ""
    var isAnswered: Boolean = false
    lateinit var scoreCategory: String

    //radioButtons
    lateinit var rg: RadioGroup
    lateinit var rb: RadioButton
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

    //profile
    var profileName = ObservableField("")
    var profileTitle = ObservableField("")

    private val gson by lazy { Gson() }
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

    fun loadHighScores() {
        val highScores = sharedPrefs.getHighScores()
        categories = highScores.categories
        scores = highScores.scores
    }

    fun scorePercentage(score: Int) : Int {
        val percentage = (score.toDouble() / 4) * 100
        return percentage.toInt()
    }

    val errorMessage: MutableLiveData<Int?> = MutableLiveData()
    val errorClickListener = View.OnClickListener { getQuizData() }

    val mutableLoading: MutableLiveData<Int?> = MutableLiveData()

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
            }
        } catch (e: IllegalThreadStateException) {
            onRetrieveDataError()
            Timber.e("$e !!! $errorMessage")
        } finally {
            onRetrieveDataFinish()
        }
    }

    fun getQData() { //sets up the DetailFragment
        try {
            scope.launch {
                mutableLoading.value = View.VISIBLE
                val qData = repo.getQData()
                _catLiveData.value = qData
            }
        } catch (e: IllegalThreadStateException) {
            Timber.e("$e")
        } finally {
            onRetrieveDataFinish()
        }
    }

    private fun cancelRequest() = coroutineContext.cancel()

    private fun onRetrieveDataSuccess() {
        mutableLoading.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveDataError() {
        errorMessage.value = R.string.error_message
        cancelRequest()
    }

    private fun onRetrieveDataFinish() {
        mutableLoading.value = View.GONE
    }

    override fun onCleared() {
        super.onCleared()
        cancelRequest()
    }

}
