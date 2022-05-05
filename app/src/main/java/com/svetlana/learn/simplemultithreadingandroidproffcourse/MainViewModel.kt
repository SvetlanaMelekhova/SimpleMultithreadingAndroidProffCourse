package com.svetlana.learn.simplemultithreadingandroidproffcourse

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    fun method() {
        val job = viewModelScope.launch(Dispatchers.Default) {
            Log.d(LOG_TAG, "Coroutine started")
            val before = System.currentTimeMillis()
            var count = 0
            for (i in 0..100_000_000) {
                for (j in 0..100) {
                    ensureActive()
                    count++
                    /*if (isActive) {
                        count++
                    } else {
                        throw CancellationException()
                    }*/
                }
            }
            Log.d(LOG_TAG, " Finished: ${System.currentTimeMillis() - before}")
        }
        job.invokeOnCompletion {
            Log.d(LOG_TAG, "Coroutine was canceled. $it")
        }
        viewModelScope.launch {
            delay(3000)
            job.cancel()
        }
    }

    companion object {
        private const val LOG_TAG = "MainViewModel"
    }
}