package com.example.bestpracticesapplication.animations

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.fragment.app.Fragment
import com.example.bestpracticesapplication.compose.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimationExampleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MyTheme {
                    val configuration = LocalConfiguration.current
                    when (configuration.orientation) {
                        Configuration.ORIENTATION_PORTRAIT -> {
                            AnimationExampleScreen()
                        }
                        else -> {
                            AnimationExampleScreen()
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun AnimationExampleScreen(
        modifier: Modifier = Modifier,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = ApplicationTheme.colors.backgroundColor,
            topBar = {
                 // App Bar
            },
            content = {
                AnimationExampleContentScreen()
            },
        )
    }

    @Composable
    private fun MyTheme(
        colors: MyThemeColors = if (isSystemInDarkTheme()) DarkMode else LightMode,
        content: @Composable () -> Unit,
    ) {
        CompositionLocalProvider(LocalExtendsColors provides colors) {
            MaterialTheme(
                colors = MaterialTheme.colors.copy(
                    primary = colors.primaryColor,
                    secondary = colors.secondaryColor,
                    background = colors.backgroundColor,
                ),
                content = content,
            )
        }
    }
}