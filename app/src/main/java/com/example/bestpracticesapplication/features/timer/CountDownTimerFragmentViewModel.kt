package com.example.bestpracticesapplication.features.timer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountDownTimerFragmentViewModel @Inject constructor(

): ViewModel() {

    internal var timeRemaining by mutableStateOf(0L)
        private set

    internal fun updateTimeRemaining(time: Long) {
        timeRemaining = time
    }

}