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
package com.ajsherrell.android.quiz_hyperbaric.utils

import android.content.Context
import com.ajsherrell.android.quiz_hyperbaric.model.Profile
import com.ajsherrell.android.quiz_hyperbaric.model.Scores
import com.google.gson.Gson

class SharedPreferenceHelper(context: Context) {

    companion object {
        const val NAME = "com.ajsherrell.android.quiz_hyperbaric"
        const val PROFILE = "com.ajsherrell.android.quiz_hyperbaric.utils.profile"
        const val SCORES = "com.ajsherrell.android.quiz_hyperbaric.utils.scores"
    }

    private val gson by lazy { Gson() }

    private val sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    fun clearHighScores() {
        sharedPreferences.edit().clear().apply()
    }

    fun getProfile(): Profile {
        val jsonString = sharedPreferences.getString(PROFILE, null)
        return if (jsonString == null) {
            Profile()
        } else {
            gson.fromJson(jsonString, Profile::class.java)
        }
    }

    fun saveProfile(name: String, title: String) {
        with(sharedPreferences.edit()) {
            putString(PROFILE, gson.toJson(Profile(name, title)))
            apply()
        }
    }

    fun getHighScores(): Scores {
        val jsonString = sharedPreferences.getString(SCORES, null)
        return if (jsonString == null) {
            Scores()
        } else {
            gson.fromJson(jsonString, Scores::class.java)
        }
    }

    fun saveHighScores(categories: MutableList<String>, scores: MutableList<String>) {
        with(sharedPreferences.edit()) {
            putString(SCORES, gson.toJson(Scores(categories, scores)))
            apply()
        }
    }
}