package com.globant.viewmodels.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

open class BaseViewModel(private val job: CompletableJob, dispatcher: CoroutineDispatcher) : ViewModel() {

    val viewModelCoroutineScope = CoroutineScope(job + dispatcher)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
