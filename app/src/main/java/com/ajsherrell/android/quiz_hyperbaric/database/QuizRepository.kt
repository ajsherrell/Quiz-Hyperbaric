package com.ajsherrell.android.quiz_hyperbaric.database

import com.ajsherrell.android.quiz_hyperbaric.model.Category
import com.ajsherrell.android.quiz_hyperbaric.model.Response
import com.ajsherrell.android.quiz_hyperbaric.network.QuizApi
import timber.log.Timber
import java.io.IOException

class QuizRepository(private val api: QuizApi) : BaseRepository() {

    suspend fun getQuizData() : Response? {
        return safeApiCall(
                call = { api.getQuizAsync().await() },
                error = "Error fetching quiz data!!!"
            )
    }

    suspend fun getQData() : MutableList<Category>? {
        return safeApiCall(
            call = {api.getQuizAsync().await()},
            error = "Error fetching q data!!!"
        )?.category?.toMutableList()
    }
}

sealed class Output<out T: Any> {
    data class Success<out T: Any>(val output: T) : Output<T>()
    data class Error(val exception: Exception) : Output<Nothing>()
}

open class BaseRepository {
    suspend fun <T: Any> safeApiCall(call: suspend() -> retrofit2.Response<T>, error : String) : T? {
        val result = quizApiOutput(call, error)
        var output : T? = null
        when(result) {
            is Output.Success ->
                output = result.output
            is Output.Error -> Timber.e("!!!The $error and the ${result.exception}")
        }
        return output
    }

    private suspend fun <T : Any> quizApiOutput(call: suspend() -> retrofit2.Response<T>, error: String) : Output<T> {
        val response = call.invoke()
        return if (response.isSuccessful)
            Output.Success(response.body()!!)
        else
            Output.Error(IOException("Oops... something is wrong!!! $error"))
    }
}