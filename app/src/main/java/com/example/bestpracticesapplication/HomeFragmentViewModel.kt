package com.example.bestpracticesapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestpracticesapplication.coroutine.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // log the error, handle the exception
    }

    init {
        viewModelScope.launch(dispatcherProvider.io() + exceptionHandler) {

        }
    }

}