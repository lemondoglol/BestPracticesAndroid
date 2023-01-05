package com.example.bestpracticesapplication.coroutine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class AvoidGlobalScopeFragmentViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
): ViewModel() {

    internal var totalExecuteTime by mutableStateOf(0L)
    internal var fakeAPIResponse by mutableStateOf("")
    private val lock = Mutex()

    /**
     * Just mimic API call, and it will always take 2 sec to finish the call
     * */
    private suspend fun fakeAPICall(i: Int) {
        delay(2000L)
        lock.withLock {
            fakeAPIResponse += "API $i call done \n"
        }
    }

    internal fun exampleUsingLaunch() {
        fakeAPIResponse = ""
        totalExecuteTime = measureTimeMillis {
            // Runs a new coroutine and blocks the current thread interruptibility until its completion.
            runBlocking {
                // these scope and dispatch will inherit from runBlocking
                launch { fakeAPICall(1) }
                launch { fakeAPICall(2) }
            }
        }
    }

    internal fun exampleUsingGlobalScope() {
        fakeAPIResponse = ""
        totalExecuteTime = measureTimeMillis {
            runBlocking {
                /**
                 * note, this already gave the warning
                 *
                 * This is a delicate API and its use requires care.
                 * Make sure you fully read and understand documentation of the declaration
                 * that is marked as a delicate API.
                 *
                 * problem here:
                 * GlobalScope.launch will launch a global coroutine
                 * 1. we lose the control once it is launched from global scope
                 * 2. if we want to cancel it, then we have to keep tracking the job that we launched,
                 *      this is not good when there are a lot of tasks that are launched from GlobalScope
                 *      cancel happens a lot, if we move one fragment to another,
                 *      we might want to cancel the current one
                 * */
                GlobalScope.launch { fakeAPICall(1) }
                GlobalScope.launch { fakeAPICall(2) }
            }
        }
    }

    /**
     * will async, but not consistant
     * */
    internal fun exampleUsingGlobalScope1() {
        fakeAPIResponse = ""
        totalExecuteTime = measureTimeMillis {
            runBlocking {
                val jobs = mutableListOf<Job>()

                jobs += GlobalScope.launch { fakeAPICall(1) }
                jobs += GlobalScope.launch { fakeAPICall(2) }

                jobs.joinAll()
            }
        }
    }

    internal fun exampleUsingCustomScope() {
        fakeAPIResponse = ""
        totalExecuteTime = measureTimeMillis {
            runBlocking {
                launch { fakeAPICallRightVersion(1) }
                launch { fakeAPICallRightVersion(2) }
            }
        }
    }

//    private suspend fun fakeAPICallRightVersion(i: Int) = withContext(dispatcherProvider.io()) {
    private suspend fun fakeAPICallRightVersion(i: Int) = withContext(Dispatchers.IO) {
        delay(2000L)
        fakeAPIResponse += "API $i call done \n"
    }
}