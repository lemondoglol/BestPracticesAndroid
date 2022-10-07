package com.example.bestpracticesapplication.features.file_transfer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        when (uri != null) {
            true -> {
                Toast.makeText(context, "URI: $uri", Toast.LENGTH_SHORT).show()
            }
            false -> {
                Toast.makeText(context, "URI is null", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
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

                        Button(
                            onClick = viewModel::writeToAppSpecificFile
                        ) {
                            Text("Click to write to a file")
                        }

                        Button(
                            onClick = {
                                pickMedia.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                                )
                                // other possible parameters
//                                ActivityResultContracts.PickVisualMedia.ImageOnly
//                                ActivityResultContracts.PickVisualMedia.VideoOnly
//                                val mimeType = "image/gif"
//                                ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)
                            }
                        ) {
                            Text("Launch Photo picker")
                        }

//                        Text("Content: ${viewModel.readAppSpecificFile()}")
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