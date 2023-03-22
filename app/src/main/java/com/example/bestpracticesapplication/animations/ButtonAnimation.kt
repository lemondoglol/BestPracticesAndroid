package com.example.bestpracticesapplication.animations

import android.util.Log
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
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountTree
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
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

fun Modifier.gradientClick(
    interactionSource: InteractionSource,
) = composed {
    val isPressed by interactionSource.collectIsPressedAsState()

    val fadeInAnimatedColor by animateColorAsState(
        targetValue = Color.Cyan,
        animationSpec = tween(durationMillis = 0)
    )

    val fadeOutAnimatedColor by animateColorAsState(
        targetValue = when (isPressed) {
            true -> Color.Cyan
            false -> Color.DarkGray
        },
        // key point!!!
        animationSpec = when (isPressed) {
            true -> tween(durationMillis = 0)
            false -> tween(durationMillis = 2000)
        }
    )

    val brush = when (isPressed) {
        true -> Brush.verticalGradient(
            colorStops = arrayOf(
                0.2f to Color.DarkGray,
                0.5f to fadeInAnimatedColor,
                0.8f to Color.DarkGray,
            )
        )
        false -> Brush.verticalGradient(
            colorStops = arrayOf(
                0.2f to Color.DarkGray,
                0.5f to fadeOutAnimatedColor,
                0.8f to Color.DarkGray,
            )
        )
    }

    this.background(brush)
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
    viewPressedSize: Float = 0.5f,
    animationDurationMills: Int = 2000,
) = composed {
    var bounceViewState by remember { mutableStateOf(ViewState.Idle) }
    val scale by animateFloatAsState(
        targetValue = if (bounceViewState == ViewState.Pressed) viewPressedSize else 1f,
        animationSpec = tween(
            durationMillis = animationDurationMills,
            easing = LinearOutSlowInEasing
        )
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

@Composable
fun Modifier.gradientIndication(
    indication: Indication,
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit,
): Modifier = composed {
    this.clickable(
        indication = indication,
        interactionSource = interactionSource,
    ) {
        onClick()
    }
}

@Composable
fun ButtonWithState(
    modifier: Modifier = Modifier,
    interactionSource: InteractionSource,
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        when (isPressed) {
            true -> {
                Icon(
                    Icons.Rounded.ShoppingCart,
                    contentDescription = ""
                )
            }
            false -> {
                Icon(
                    Icons.Rounded.AccountTree,
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
fun AnimatedShimmerBrush(

): Brush {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )
    return brush
}
