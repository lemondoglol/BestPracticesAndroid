package com.example.bestpracticesapplication.features.workmanager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bestpracticesapplication.coroutine.DispatcherProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltWorker
class MyWorker @AssistedInject constructor(
    @Assisted context: Context, // required
    @Assisted workerParameters: WorkerParameters, // required
    private val dispatcherProvider: DispatcherProvider,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        CoroutineScope(dispatcherProvider.io()).launch {
            delay(500L)
            Log.d("Tuna", "Worker: job's done")
        }

        return Result.success()
    }
}