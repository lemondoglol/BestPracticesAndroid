package com.example.bestpracticesapplication.features.file_transfer

import android.content.Context
import android.os.Environment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestpracticesapplication.coroutine.DispatcherProvider
import com.example.bestpracticesapplication.networking.FileTransferServiceClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class FileTransferFragmentViewModel @Inject constructor(
    private val context: Context,
    private val dispatcherProvider: DispatcherProvider,
    private val fileTransferServiceClient: FileTransferServiceClient,
) : ViewModel() {

    internal var savedFileUri by mutableStateOf("")
        private set

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // log the error, handle the exception
    }

    internal fun downloadAndSaveFile() {
        viewModelScope.launch(dispatcherProvider.io() + exceptionHandler) {
            val response = fileTransferServiceClient.downloadFile(
                "wp-content/uploads/2021/11/05132702/WoWClassic-ArathiBasin.jpg"
            )

            // save file
            when (response.isSuccessful) {
                true -> {
                    response.body()?.byteStream().let { inputStream ->
                        kotlin.runCatching {
                            val file = File(
                                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                                "wow1.jpg"
                            )
                            if (!file.exists()) file.createNewFile()
                            val fileOutputStream = FileOutputStream(file)
                            inputStream?.copyTo(fileOutputStream)
                            savedFileUri = file.path
                        }
                    }
                }
                false -> {
                    savedFileUri = "Couldn't download/save file"
                }
            }
        }
    }
}