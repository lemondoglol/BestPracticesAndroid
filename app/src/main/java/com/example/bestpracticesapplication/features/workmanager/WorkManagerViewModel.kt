package com.example.bestpracticesapplication.features.workmanager

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkManagerViewModel @Inject constructor(
    private val context: Context
): ViewModel() {

    internal fun startOneTimeRequest() {
        val workRequest = OneTimeWorkRequestBuilder<MyWorker>().build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }

}