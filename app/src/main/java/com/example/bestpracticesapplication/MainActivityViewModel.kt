package com.example.bestpracticesapplication

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(

) : ViewModel() {

    internal var prevX = 0f
    internal var prevY = 0f
    internal var prevZ = 0f
    internal var prevTime = 0L

}