package com.ajsherrell.android.quiz_hyperbaric.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.ajsherrell.android.quiz_hyperbaric.model.Response

@Dao
interface QuizDao {

    @Query("SELECT * FROM response")
    fun getResponse(): LiveData<List<Response>>

}