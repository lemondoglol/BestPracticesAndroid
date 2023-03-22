package com.example.bestpracticesapplication.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

private enum class ViewState { Pressed, Idle }

/**
 * when the view is clicked:
 * - click indication shows up immediately
 * - fade out indication slowly fade out
 * */
fun Modifier.gradientClick(
    interactionSource: InteractionSource,
    idleStateColor: Color = Color.Cyan,
    pressStateColor: Color = Color.DarkGray,
    fadeInDuration: Int = 0,
    fadeOutDuration: Int = 1000,
) = composed {
    val isPressed by interactionSource.collectIsPressedAsState()

    val fadeInAnimatedColor by animateColorAsState(
        targetValue = idleStateColor,
        animationSpec = tween(durationMillis = fadeInDuration)
    )

    val fadeOutAnimatedColor by animateColorAsState(
        targetValue = when (isPressed) {
            true -> idleStateColor
            false -> pressStateColor
        },
        animationSpec = when (isPressed) {
            true -> tween(durationMillis = fadeInDuration)
            false -> tween(durationMillis = fadeOutDuration)
        }
    )

    val brush = when (isPressed) {
        true -> Brush.verticalGradient(
            colorStops = arrayOf(
                0.2f to pressStateColor,
                0.5f to fadeInAnimatedColor,
                0.8f to pressStateColor,
            )
        )
        false -> Brush.verticalGradient(
            colorStops = arrayOf(
                0.2f to pressStateColor,
                0.5f to fadeOutAnimatedColor,
                0.8f to pressStateColor,
            )
        )
    }

    this.background(brush)
}

fun Modifier.bounceClick(
    interactionSource: InteractionSource,
    viewPressedSize: Float = 0.5f,
    animationDurationMills: Int = 2000,
) = composed {
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) viewPressedSize else 1f,
        animationSpec = tween(
            durationMillis = animationDurationMills,
            easing = LinearOutSlowInEasing
        )
    )

    this.scale(scale)
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

fun Modifier.customIndication(
    indication: Indication,
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit,
): Modifier =
    this.clickable(
        indication = indication,
        interactionSource = interactionSource,
    ) {
        onClick()
    }
