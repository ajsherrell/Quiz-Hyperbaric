package com.ajsherrell.android.quiz_hyperbaric.viewModel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ajsherrell.android.quiz_hyperbaric.QuizApplication
import com.ajsherrell.android.quiz_hyperbaric.database.QuizRepository
import com.ajsherrell.android.quiz_hyperbaric.network.NetworkModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException


class QuizListViewModel: AndroidViewModel(QuizApplication()) {
    private val repository = QuizRepository()

    private val job = Job()

    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _refreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    val refreshing :LiveData<Boolean>
        get() = _refreshing

    fun refreshDataFromRepo() {
        coroutineScope.launch {
            try {
                _refreshing.value = true
                repository.getResponse()
                Timber.d("Data refreshed from repository!!!")
            } catch (e: IOException) {
                "error fetching Quiz Api: ${e.message}".apply {
                    _errorMessage.value = this
                    Timber.e(this)
                }
                e.printStackTrace()
            } finally {
                _refreshing.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}