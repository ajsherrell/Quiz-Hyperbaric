package com.ajsherrell.android.quiz_hyperbaric.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Response(
    @Json(name = "category")
    @PrimaryKey @ColumnInfo(name = "category") val category: List<Category> = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class Category(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "questions") val questions: List<Questions> = mutableListOf()
)

@JsonClass(generateAdapter = true)
data class Questions(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "answer") val answer: String,
    @ColumnInfo(name = "options") val options: List<Options> = mutableListOf(),
    @ColumnInfo(name = "questionText") val questionText: String
)

@JsonClass(generateAdapter = true)
data class Options(
    @ColumnInfo(name = "options") val options: List<String>
)