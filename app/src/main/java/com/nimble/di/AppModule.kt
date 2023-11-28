package com.nimble.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nimble.BuildConfig
import com.nimble.base.AppConstants
import com.nimble.data.local.SurveyDao
import com.nimble.data.remote.NimbleAppApi
import com.nimble.data.remote.NimbleAuthApi
import com.nimble.di.repository.LocalAppRepository
import com.nimble.di.repository.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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

    //HTTP logging interceptor
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    //HTTP Client and set logging interceptor and OAuth API interceptor
    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        userPreferencesRepository: UserPreferencesRepository
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(AppConstants.HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS) // Timeout for establishing a connection
            .readTimeout(AppConstants.HTTP_READ_TIMEOUT, TimeUnit.SECONDS)    // Timeout for reading data
            .writeTimeout(AppConstants.HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)   // Timeout for writing data
            .addInterceptor(interceptor)
            .addInterceptor(AuthInterceptor(userPreferencesRepository))
            .build()
    }

    //Create Retrofit client
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Create Auth API service
    @Provides
    fun providesAuthApi(retrofit: Retrofit): NimbleAuthApi {
        return retrofit.create(NimbleAuthApi::class.java)
    }

    //Create APP API service
    @Provides
    fun providesAppApi(retrofit: Retrofit): NimbleAppApi {
        return retrofit.create(NimbleAppApi::class.java)
    }

    //Create DataStore preference repo
    @Provides
    @Singleton
    fun provideUserDataStorePreferences(
        @ApplicationContext applicationContext: Context
    ): DataStore<Preferences> {
        return applicationContext.userDataStore
    }

    //Database create functions goes here
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getInstance(appContext)

    @Singleton
    @Provides
    fun provideCountryDao(db: AppDatabase) = db.surveyDao()

    @Singleton
    @Provides
    fun providePostRepository(surveyDao: SurveyDao): LocalAppRepository {
        return LocalAppRepository(surveyDao)
    }

    //GSON for json converter class
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()
}