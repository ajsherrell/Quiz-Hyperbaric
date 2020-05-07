package com.ajsherrell.android.quiz_hyperbaric.database

import androidx.lifecycle.MutableLiveData
import com.ajsherrell.android.quiz_hyperbaric.model.Response
import com.ajsherrell.android.quiz_hyperbaric.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class QuizRepository {

    private val service: ApiService = ApiService

//    private val liveData = MutableLiveData<Response>()
    private suspend fun getData() {
//        liveData.value = service.quizApi.getQuiz()
        service.quizApi.getQuiz()
    }

    suspend fun getResponse() {
        withContext(Dispatchers.IO) {
            getData()
        }
    }
}