package com.example.bestpracticesapplication.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

sealed class MyThemeColors(
    val primaryColor: Color,
    val secondaryColor: Color,
    val backgroundColor: Color,
)

object DarkMode : MyThemeColors(
    primaryColor = Color.White,
    secondaryColor = Color.Cyan,
    backgroundColor = Color.DarkGray,
)

object LightMode : MyThemeColors(
    primaryColor = Color.Black,
    secondaryColor = Color.DarkGray,
    backgroundColor = Color.DarkGray,
)

val LocalExtendsColors = staticCompositionLocalOf<MyThemeColors> {
    DarkMode
}

object ApplicationTheme {
    val colors: MyThemeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalExtendsColors.current
}