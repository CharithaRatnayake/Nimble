package com.nimble.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nimble.BuildConfig
import com.nimble.base.AppConstants
import com.nimble.data.local.UserPreferencesRepository
import com.nimble.data.remote.NimbleAppApi
import com.nimble.data.remote.NimbleAuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

    private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
        name = AppConstants.APP_DATASTORE_NAME
    )

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun provideAuthTokenInterceptor(userPreferencesRepository: UserPreferencesRepository): AuthInterceptor {
        return AuthInterceptor(userPreferencesRepository)
    }

    @Provides
    fun providesAuthApi(retrofit: Retrofit): NimbleAuthApi {
        return retrofit.create(NimbleAuthApi::class.java)
    }

    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(BearerTokenInterceptor("uFJoWJTNM_hq8llpOe9dhdwXK6CHzUwX6yGazrkmorY"))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun providesAppApi(retrofit: Retrofit): NimbleAppApi {
        return retrofit.create(NimbleAppApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserDataStorePreferences(
        @ApplicationContext applicationContext: Context
    ): DataStore<Preferences> {
        return applicationContext.userDataStore
    }

}