package com.example.bestpracticesapplication.networking

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface FileTransferServiceClient {

    @GET
    @Streaming
    suspend fun downloadFile(@Url url: String): Response<ResponseBody>

    // TODO
    @PUT
    suspend fun uploadFile(@Url url: String, @Body file: RequestBody): Response<ResponseBody>
}