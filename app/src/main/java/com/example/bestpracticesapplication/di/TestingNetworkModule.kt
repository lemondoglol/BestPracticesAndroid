package com.example.bestpracticesapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Issue: We have 2 methods that return the same type, but different implementation
     * Solution: Use @Qualifier, @Retention
     * */
    @HttpClient1
    @Provides
    fun provideHttpClient1(
        repo1: String
    ): OkHttpClient = OkHttpClient()

    @HttpClient2
    @Provides
    fun provideHttpClient2(
        repo2: String
    ): OkHttpClient = OkHttpClient()
}

/**
 * testing only
 * */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HttpClient1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HttpClient2


/***
 * for the usage
 */
//class ExampleServiceImpl @Inject constructor(
//    @HttpClient1 private val okHttpClient: OkHttpClient
//) : ...