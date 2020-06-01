package com.ajsherrell.android.quiz_hyperbaric.viewModel

import android.app.Application
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.*
import com.ajsherrell.android.quiz_hyperbaric.R
import com.ajsherrell.android.quiz_hyperbaric.database.QuizRepository
import com.ajsherrell.android.quiz_hyperbaric.model.Category
import com.ajsherrell.android.quiz_hyperbaric.model.Questions
import com.ajsherrell.android.quiz_hyperbaric.model.Response
import com.ajsherrell.android.quiz_hyperbaric.network.NetworkModule
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class QuizListViewModel(val app: Application) : AndroidViewModel(app) {

    private val job = Job()

    private val coroutineContext : CoroutineContext get() = job + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    private val repo : QuizRepository = QuizRepository(NetworkModule.quizApi)

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

    var score = 0
    var questionBank = listOf<Questions>()
    var currentIndex = 0
    var currentQuestionId = 0
    var answer = ""
    var isAnswered: Boolean = false
    var selectedAnswerText = ""
    var selectedId = 0
    var isCorrect = false

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
