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
package com.ajsherrell.android.quiz_hyperbaric.database

import com.ajsherrell.android.quiz_hyperbaric.model.Category
import com.ajsherrell.android.quiz_hyperbaric.model.Response
import com.ajsherrell.android.quiz_hyperbaric.network.QuizApi
import timber.log.Timber
import java.io.IOException

class QuizRepository(private val api: QuizApi) : BaseRepository() {

    suspend fun getRepoQuizListData() : Response? {
        return safeApiCall(
                call = { api.getQuizAsync().await() },
                error = "Error fetching quiz list data!!!"
            )
    }

    suspend fun getRepoQuizDetailData() : MutableList<Category>? {
        return safeApiCall(
            call = {api.getQuizAsync().await()},
            error = "Error fetching quiz detail data!!!"
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