package com.example.bestpracticesapplication.di

import com.example.bestpracticesapplication.networking.FileTransferServiceClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FileTransferModule {

    @Provides
    @Singleton
    internal fun provideFileTransferServiceClient(
        @Named(FILE_TRANSFER_RETROFIT) retrofit: Retrofit
    ) = retrofit.create(FileTransferServiceClient::class.java)

    /**
     * the full url to download in this example
     * https://cdn1.dotesports.com/wp-content/uploads/2021/11/05132702/WoWClassic-ArathiBasin.jpg
     * */
    @Provides
    @Singleton
    @Named(FILE_TRANSFER_RETROFIT)
    fun provideFileTransferRetrofit(): Retrofit = Retrofit
        .Builder()
        .baseUrl("https://cdn1.dotesports.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private const val FILE_TRANSFER_RETROFIT = "fileTransferBuilder"
}