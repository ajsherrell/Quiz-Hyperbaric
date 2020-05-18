package com.ajsherrell.android.quiz_hyperbaric.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ajsherrell.android.quiz_hyperbaric.database.QuizRepository
import com.ajsherrell.android.quiz_hyperbaric.model.Category
import com.ajsherrell.android.quiz_hyperbaric.model.Response
import com.ajsherrell.android.quiz_hyperbaric.network.NetworkModule
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class QuizListViewModel : ViewModel() {
    private val job = Job()

    private val coroutineContext : CoroutineContext get() = job + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    private val repo : QuizRepository = QuizRepository(NetworkModule.quizApi)

    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = mutableLoading


    private val _quizLiveData: MutableLiveData<Response> = MutableLiveData()
    val quizLiveData: LiveData <Response>
        get() = _quizLiveData

    private val _catLiveData: MutableLiveData<List<Category>> = MutableLiveData()
    val catLiveData: LiveData<List<Category>>
        get() = _catLiveData

    fun getQuizData() { //sets up the ListFragment
        try {
            scope.launch {
                mutableLoading.value = true
                val quizData = repo.getQuizData()
                _quizLiveData.value = quizData
            }
        } catch (e: IllegalThreadStateException) {
            Timber.e("$e")
        } finally {
            mutableLoading.value = false
        }
    }

    fun getQData() { //sets up the DetailFragment
        try {
            scope.launch {
                val qData = repo.getQData()
                _catLiveData.value = qData
            }
        } catch (e: IllegalThreadStateException) {
            Timber.e("$e")
        }
    }

    fun cancelRequest() = coroutineContext.cancel() //todo: where to call?
}

@Suppress("UNCHECKED_CAST")
class QuizFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuizListViewModel() as T
    }
}
