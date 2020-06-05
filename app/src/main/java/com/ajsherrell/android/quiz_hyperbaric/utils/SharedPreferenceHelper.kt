package com.ajsherrell.android.quiz_hyperbaric.utils

import android.content.Context
import com.ajsherrell.android.quiz_hyperbaric.model.Profile
import com.google.gson.Gson

class SharedPreferenceHelper(context: Context) {

    companion object {
        const val NAME = "quiz_app"
        const val PROFILE = "profile"
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

}