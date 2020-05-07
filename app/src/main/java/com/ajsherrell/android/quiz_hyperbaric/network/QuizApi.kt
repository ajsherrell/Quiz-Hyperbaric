package com.ajsherrell.android.quiz_hyperbaric.network

import com.ajsherrell.android.quiz_hyperbaric.model.Response
import retrofit2.http.GET

interface QuizApi {

    @GET("/68a0fdf29ebd9dc2774d/")
    suspend fun getQuiz(): Response

}