package com.example.bestpracticesapplication

import androidx.lifecycle.ViewModel
import com.example.bestpracticesapplication.coroutine.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

}