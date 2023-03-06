package com.example.bestpracticesapplication.animations

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalComposeUiApi::class)
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

        Column(
            modifier = modifier.brushClick(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Button(
                modifier = Modifier.shakeClick(),
                onClick = {
                          Log.d("Tuna", "parent clicked")
                },
            ) {
                Text("Shake Button: Click Me!")
            }

            Button(
                modifier = Modifier
                    .bounceClick()
                    .aspectRatio(ratio = 1f, matchHeightConstraintsFirst = false)
                    .clip(CircleShape),
                onClick = {
                    Log.d("Tuna", "child clicked")
                }
            ) {
                Text("Bounce Gradient Button: Click Me!")
            }
        }
    }
}
