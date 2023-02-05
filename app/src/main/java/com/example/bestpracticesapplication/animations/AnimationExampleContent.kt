package com.example.bestpracticesapplication.animations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDirection.Companion.Content

@Composable
fun AnimationExampleContentScreen(
    modifier: Modifier = Modifier,
) {
    var isClicked by remember { mutableStateOf(false) }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            modifier = Modifier.shiftClick(),
            onClick = {},
        ) {
            Text("Shift Button: Click Me!")
        }

        Button(
            modifier = Modifier.shakeClick(),
            onClick = {},
        ) {
            Text("Shake Button: Click Me!")
        }

        Button(
            modifier = Modifier
                .onClickGradientEffect(
                    idleStateGradientColors = listOf(
                        Color.DarkGray,
                        Color.DarkGray,
                    ),
                    pressStateGradientColors = listOf(
                        Color.Gray,
                        Color.DarkGray,
                    )
                )
                .bounceClick()
                .aspectRatio(ratio = 1f, matchHeightConstraintsFirst = false)
                .clip(CircleShape),
            onClick = { isClicked = !isClicked }
        ) {
            Text("Bounce Gradient Button: Click Me!")
        }
    }
}
