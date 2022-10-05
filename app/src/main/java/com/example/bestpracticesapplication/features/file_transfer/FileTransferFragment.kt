package com.example.bestpracticesapplication.features.file_transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bestpracticesapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FileTransferFragment : Fragment() {

    private val viewModel by viewModels<FileTransferFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column {
                        Button(
                            onClick = viewModel::downloadAndSaveFile
                        ) {
                            Text("Click to download and save file")
                        }

                        if (viewModel.savedFileUri.isNotBlank()) {
                            LoadImageFromUri(
                                uri = viewModel.savedFileUri,
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun LoadImageFromUri(
        uri: String,
        modifier: Modifier = Modifier,
    ) {
        Box(modifier = modifier.height(355.dp).width(355.dp)) {
            AsyncImage(
                modifier = Modifier.clip(CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uri)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "cont",
                contentScale = ContentScale.Crop,
            )
        }
    }
}