package com.ajsherrell.android.quiz_hyperbaric.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ajsherrell.android.quiz_hyperbaric.database.QuizRepository
import com.ajsherrell.android.quiz_hyperbaric.model.Category
import com.ajsherrell.android.quiz_hyperbaric.network.NetworkModule
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class QuizListViewModel : ViewModel() {
    private val job = Job()

    private val coroutineContext : CoroutineContext get() = job + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repo : QuizRepository = QuizRepository(NetworkModule.quizApi)

    val quizLiveData = MutableLiveData<MutableList<Category>>()

    fun getQuizData() {
        scope.launch {
            val quizData = repo.getQuizData()
            quizLiveData.postValue(quizData)
        }
    }

    fun cancelRequest() = coroutineContext.cancel()
}

@Suppress("UNCHECKED_CAST")
class QuizFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuizListViewModel() as T
    }
}
