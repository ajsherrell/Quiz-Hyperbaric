package com.ajsherrell.android.quiz_hyperbaric.network

import com.ajsherrell.android.quiz_hyperbaric.model.Response
import retrofit2.Call
import retrofit2.http.GET

interface QuizApi {

    @GET
    fun getQuiz(): Call<Response>

}