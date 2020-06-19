/*
 * Copyright 2020 AJ Sherrell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    var title: String,
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
    val options: List<String>,
    @field:Json(name = "answered")
    val answered: Boolean = false,
    @field:Json(name = "questionText")
    val questionText: String
) {
    var randomAnswers = options.shuffled()
}

data class Profile(val name: String = "", val title: String = "")

data class Scores(val categories: MutableList<String> = mutableListOf(),
                  val scores: MutableList<String> = mutableListOf()
)

