package com.ajsherrell.android.quiz_hyperbaric.database

import androidx.lifecycle.MutableLiveData
import com.ajsherrell.android.quiz_hyperbaric.model.Response
import com.ajsherrell.android.quiz_hyperbaric.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuizRepository {
    private val nm = NetworkModule

    private val liveDataList = MutableLiveData<List<Response>>()

    suspend fun getResponse() {
        return withContext(Dispatchers.IO) {
            liveDataList.value = nm.apiService.getQuiz()
        }
    }

}