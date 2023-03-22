package com.example.bestpracticesapplication.animations

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.bestpracticesapplication.compose.ButtonWithState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AnimationExampleContentScreen(
    modifier: Modifier = Modifier,
) {
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

        val gradientInteractionSource = remember { MutableInteractionSource() }
        Column(
            modifier = modifier.gradientClick(
                interactionSource = gradientInteractionSource
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Button(
                modifier = Modifier.shakeClick(),
                onClick = {},
            ) {
                Text("Shake Button: Click Me!")
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick()
                    .aspectRatio(ratio = 5.5f, matchHeightConstraintsFirst = false)
                    .clip(CircleShape),
                onClick = {}
            ) {
                Text("Bounce Button: Click Me!")
            }

            // this will trigger the same click to the same interaction source
            ButtonWithState(
                modifier = Modifier
                    .customIndication(
                        interactionSource = gradientInteractionSource,
                        indication = rememberRipple(

                        )
                    ) {},
                interactionSource = gradientInteractionSource,
            )
        }
    }
}
