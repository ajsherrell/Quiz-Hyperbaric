package com.ajsherrell.android.quiz_hyperbaric.utils

import android.content.Context
import com.ajsherrell.android.quiz_hyperbaric.model.Category
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

    fun saveHighScores(category: String, score: String) {
        with(sharedPreferences.edit()) {
            putString(SCORES, gson.toJson(Scores(category, score)))
            apply()
        }
    }

}