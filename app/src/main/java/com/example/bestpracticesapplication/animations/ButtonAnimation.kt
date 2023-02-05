package com.example.bestpracticesapplication.animations

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

private enum class BounceViewState { Pressed, Idle }

fun Modifier.onClickGradientEffect(
    idleStateGradientColors: List<Color>,
    pressStateGradientColors: List<Color>,
) = composed {
    var bounceViewState by remember { mutableStateOf(BounceViewState.Idle) }

    val gradient = when (bounceViewState) {
        BounceViewState.Idle -> Brush.verticalGradient(
            idleStateGradientColors
        )
        BounceViewState.Pressed -> Brush.verticalGradient(
            pressStateGradientColors
        )
    }

    this
        .background(brush = gradient)
        .pointerInput(bounceViewState) {
            awaitPointerEventScope {
                bounceViewState = when (bounceViewState) {
                    BounceViewState.Pressed -> {
                        waitForUpOrCancellation()
                        BounceViewState.Idle
                    }
                    BounceViewState.Idle -> {
                        awaitFirstDown(false)
                        BounceViewState.Pressed
                    }
                }
            }
        }
}

fun Modifier.bounceClick(
    viewPressedSize: Float = 0.9f,
    animationDurationMills: Int = 1000,
) = composed {
    var bounceViewState by remember { mutableStateOf(BounceViewState.Idle) }
    val scale by animateFloatAsState(
        targetValue = if (bounceViewState == BounceViewState.Pressed) viewPressedSize else 1f,
        animationSpec = tween(durationMillis = animationDurationMills)
    )

    val modifier = this
        .scale(scale)
        .pointerInput(bounceViewState) {
            awaitPointerEventScope {
                bounceViewState = when (bounceViewState) {
                    BounceViewState.Pressed -> {
                        waitForUpOrCancellation()
                        BounceViewState.Idle
                    }
                    BounceViewState.Idle -> {
                        awaitFirstDown(false)
                        BounceViewState.Pressed
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
    var bounceViewState by remember { mutableStateOf(BounceViewState.Idle) }
    val shiftSize by animateFloatAsState(
        targetValue = if (bounceViewState == BounceViewState.Pressed) viewPressedShiftSize else 0f,
        animationSpec = tween(durationMillis = animationDurationMills)
    )

    val modifier = this
        .graphicsLayer {
            translationY = shiftSize
        }
        .pointerInput(bounceViewState) {
            awaitPointerEventScope {
                bounceViewState = when (bounceViewState) {
                    BounceViewState.Pressed -> {
                        waitForUpOrCancellation()
                        BounceViewState.Idle
                    }
                    BounceViewState.Idle -> {
                        awaitFirstDown(false)
                        BounceViewState.Pressed
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
    var bounceViewState by remember { mutableStateOf(BounceViewState.Idle) }
    val shakeSize by animateFloatAsState(
        targetValue = if (bounceViewState == BounceViewState.Pressed) viewPressedShakeSize else 0f,
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
                    BounceViewState.Pressed -> {
                        waitForUpOrCancellation()
                        BounceViewState.Idle
                    }
                    BounceViewState.Idle -> {
                        awaitFirstDown(false)
                        BounceViewState.Pressed
                    }
                }
            }
        }

    modifier
}
