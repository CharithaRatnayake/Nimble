package com.nimble.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nimble.BuildConfig
import com.nimble.data.remote.NimbleAppApi
import com.nimble.data.remote.NimbleAuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @file AppModule.kt
 * @date 11/25/2023
 * @brief AppModule class uses for di common dependencies
 * Created by Charitha Ratnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun providesAuthApi(retrofit: Retrofit): NimbleAuthApi {
        return retrofit.create(NimbleAuthApi::class.java)
    }

    @Provides
    fun providesAppApi(retrofit: Retrofit): NimbleAppApi {
        return retrofit.create(NimbleAppApi::class.java)
    }

}