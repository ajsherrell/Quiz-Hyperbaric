package com.ajsherrell.android.quiz_hyperbaric.database

import androidx.lifecycle.MutableLiveData
import com.ajsherrell.android.quiz_hyperbaric.model.Response
import com.ajsherrell.android.quiz_hyperbaric.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class QuizRepository {
//    private val api = ApiService

    private val liveDataList = MutableLiveData<retrofit2.Response<Response>>()

    suspend fun getResponse() {
        withContext(Dispatchers.IO) {
            ApiService.quizApi.getQuiz().let {
                liveDataList.value = it
            }
//            liveDataList.value = api.quizApi.getQuiz()
            Timber.d("liveDataList!!! = $liveDataList")
        }
    }
}