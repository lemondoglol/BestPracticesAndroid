package com.example.bestpracticesapplication.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * Tied to the lifetime of the application
 * */
class ApplicationCoroutineScope(context: CoroutineContext) : CoroutineScope {
    override val coroutineContext: CoroutineContext = context
}
