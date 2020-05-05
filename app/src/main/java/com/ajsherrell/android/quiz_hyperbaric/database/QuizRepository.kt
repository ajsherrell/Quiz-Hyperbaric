package com.ajsherrell.android.quiz_hyperbaric.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.ajsherrell.android.quiz_hyperbaric.model.Response
import com.ajsherrell.android.quiz_hyperbaric.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException
import javax.inject.Inject

private const val DATABASE_NAME = "quiz-database"

class QuizRepository private constructor(context: Context) {

    private val apiService = NetworkModule.apiService

    private val database: QuizDatabase = Room.databaseBuilder(
        context.applicationContext,
        QuizDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val quizDao = database.quizDao()

    suspend fun getResponse() {
        return withContext(Dispatchers.IO) {
            apiService.getQuiz().apply {
                quizDao.getResponse()
            }
        }
    }

    companion object {
        private var INSTANCE: QuizRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = QuizRepository(context)
            }
        }

        fun get(): QuizRepository {
            return INSTANCE ?:
                    throw IllegalStateException("QuizRepository must be initialized!")
        }
    }

}