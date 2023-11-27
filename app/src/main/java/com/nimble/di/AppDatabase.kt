package com.nimble.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nimble.base.AppConstants
import com.nimble.data.local.SurveyDao
import com.nimble.data.local.SurveyEntity

/**
 * @file AppDatabase
 * @date 11/27/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/27/2023.
 */

@Database(entities = [SurveyEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun surveyDao(): SurveyDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): AppDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, AppDatabase::class.java,
                    AppConstants.APP_DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!

        }
    }
}