package com.ajsherrell.android.quiz_hyperbaric.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface QuizApi {

    @GET("/3675fb31a904cc979af4/")
    fun getQuizAsync(): Deferred<Response<com.ajsherrell.android.quiz_hyperbaric.model.Response>>

}