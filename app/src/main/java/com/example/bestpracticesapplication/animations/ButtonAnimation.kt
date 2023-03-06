package com.example.bestpracticesapplication.animations

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

private enum class ViewState { Pressed, Idle }

fun Modifier.brushClick() = composed {
    var viewState by remember { mutableStateOf(ViewState.Idle) }
    val animatedColor by animateColorAsState(
        targetValue = when (viewState) {
            ViewState.Pressed -> Color.Gray
            ViewState.Idle -> Color.DarkGray
        },
        animationSpec = tween(
            delayMillis = 200,
            durationMillis = 1000,
        )
    )

    val brush = when (viewState) {
        ViewState.Pressed -> Brush.verticalGradient(
            colorStops = arrayOf(
                0.2f to Color.DarkGray,
                0.5f to animatedColor,
                0.8f to Color.DarkGray,
            )
        )
        ViewState.Idle -> Brush.verticalGradient(
            colors = listOf(Color.DarkGray, Color.DarkGray),
        )
    }

    this
        .background(brush)
        .pointerInput(viewState) {
            awaitPointerEventScope {
                viewState = when (viewState) {
                    ViewState.Idle -> {
                        // waiting for the first down event (even it is consumed by other clicks already)
                        awaitFirstDown(false)
                        ViewState.Pressed
                    }
                    ViewState.Pressed -> {
                        waitForUpOrCancellation()
                        ViewState.Idle
                    }
                }
            }
        }
}

fun Modifier.backgroundColorClick(
    viewPressedSize: Float = 0.9f,
    animationDurationMills: Int = 1000,
) = composed {
    var viewState by remember { mutableStateOf(ViewState.Idle) }
    val backgroundColor by animateColorAsState(
        targetValue = when (viewState) {
            ViewState.Pressed -> Color.Gray
            ViewState.Idle -> Color.DarkGray
        },
        animationSpec = tween(
            durationMillis = 500,
        )
    )

    val modifier = this
        .background(backgroundColor)
        .pointerInput(viewState) {
            awaitPointerEventScope {
                viewState = when (viewState) {
                    ViewState.Pressed -> {
                        waitForUpOrCancellation()
                        ViewState.Idle
                    }
                    ViewState.Idle -> {
                        awaitFirstDown(false)
                        ViewState.Pressed
                    }
                }
            }
        }

    modifier
}

fun Modifier.bounceClick(
    viewPressedSize: Float = 0.9f,
    animationDurationMills: Int = 1000,
) = composed {
    var bounceViewState by remember { mutableStateOf(ViewState.Idle) }
    val scale by animateFloatAsState(
        targetValue = if (bounceViewState == ViewState.Pressed) viewPressedSize else 1f,
        animationSpec = tween(durationMillis = animationDurationMills)
    )

    val modifier = this
        .scale(scale)
        .pointerInput(bounceViewState) {
            awaitPointerEventScope {
                bounceViewState = when (bounceViewState) {
                    ViewState.Pressed -> {
                        waitForUpOrCancellation()
                        ViewState.Idle
                    }
                    ViewState.Idle -> {
                        awaitFirstDown(false)
                        ViewState.Pressed
                    }
                }
            }
        }

    modifier
}

fun Modifier.shiftClick(
    viewPressedShiftSize: Float = 20f,
    animationDurationMills: Int = 1000,
) = composed {
    var bounceViewState by remember { mutableStateOf(ViewState.Idle) }
    val shiftSize by animateFloatAsState(
        targetValue = if (bounceViewState == ViewState.Pressed) viewPressedShiftSize else 0f,
        animationSpec = tween(durationMillis = animationDurationMills)
    )

    val modifier = this
        .graphicsLayer {
            translationY = shiftSize
        }
        .pointerInput(bounceViewState) {
            awaitPointerEventScope {
                bounceViewState = when (bounceViewState) {
                    ViewState.Pressed -> {
                        waitForUpOrCancellation()
                        ViewState.Idle
                    }
                    ViewState.Idle -> {
                        awaitFirstDown(false)
                        ViewState.Pressed
                    }
                }
            }
        }

    modifier
}

fun Modifier.shakeClick(
    viewPressedShakeSize: Float = 20f,
    animationDurationMills: Int = 100,
    iterationTimes: Int = 3,
) = composed {
    var bounceViewState by remember { mutableStateOf(ViewState.Idle) }
    val shakeSize by animateFloatAsState(
        targetValue = if (bounceViewState == ViewState.Pressed) viewPressedShakeSize else 0f,
        animationSpec = repeatable(
            iterations = iterationTimes,
            animation = tween(durationMillis = animationDurationMills, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        )
    )

    val modifier = this
        .graphicsLayer {
            translationX = shakeSize
        }
        .pointerInput(bounceViewState) {
            awaitPointerEventScope {
                bounceViewState = when (bounceViewState) {
                    ViewState.Pressed -> {
                        waitForUpOrCancellation()
                        ViewState.Idle
                    }
                    ViewState.Idle -> {
                        awaitFirstDown(false)
                        ViewState.Pressed
                    }
                }
            }
        }

    modifier
}
