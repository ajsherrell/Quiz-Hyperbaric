package com.ajsherrell.android.quiz_hyperbaric.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface QuizApi {

    @GET("/")
    fun getQuizAsync(): Deferred<Response<com.ajsherrell.android.quiz_hyperbaric.model.Response>>

}