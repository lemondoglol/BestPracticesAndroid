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
                                "wow3.jpg"
                            )
                            if (!file.exists()) file.createNewFile()
                            FileOutputStream(file).use {
                                inputStream?.copyTo(it)
                            }

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

    internal fun writeToAppSpecificFile(
        fileName: String = "myFile",
        fileContent: String = "Hello Ryan!",
    ) {
        // file stored in /data/data/com.example.bestpracticesapplication/files/myFile
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(fileContent.toByteArray())
        }
    }

    internal fun readAppSpecificFile(fileName: String = "myFile"): String {
        val content  = context.openFileInput(fileName).bufferedReader().useLines {
            it.fold("") { curItem, nextItem ->
                "$curItem\n$nextItem"
            }
        }
        return content
    }

    internal fun cachedFile() {
        val fileName = "cachedFileName"
        // create new cache file, stored in data/user/0/com.example.bestpracticesapplication/cache
        val newFile = File.createTempFile(fileName, ".txt", context.cacheDir)

        // access file
        val cachedFile = File(context.cacheDir, fileName)

        // delete file, handle this in onCleared()
//        cachedFile.delete()
        // or delete by name context.delete(fileName)
    }

    internal fun accessAppSpecificExternalStorage() {
        val fileName = "appSpecificExternalDir"
        // access
        val appSpecificExternalDir = File(context.getExternalFilesDir(null), fileName)

        // create a cached file in appSpecificExternalDir
        val cachedFile = File(context.externalCacheDir, fileName)

        // remove cached file
        cachedFile.delete()

        // example for get the pictures directory
        val albumName = "myAlbumName"
        val pictureFile = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), albumName)
        if (!pictureFile.mkdirs()) {
            // Directory not created
        }
        // return pictureFile
    }

}