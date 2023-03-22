package com.example.bestpracticesapplication.compose

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountTree
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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
