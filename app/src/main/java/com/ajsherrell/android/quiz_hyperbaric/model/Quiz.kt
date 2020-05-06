package com.ajsherrell.android.quiz_hyperbaric.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    @field:Json(name = "category")
    val category: List<Category> = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class Category(
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "questions")
    val questions: List<Questions> = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class Questions(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "answer")
    val answer: String,
    @field:Json(name = "options")
    val options: List<String> = mutableListOf(),
    @field:Json(name = "questionText")
    val questionText: String
)
