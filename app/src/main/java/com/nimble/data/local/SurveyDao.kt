package com.nimble.data.local

import androidx.annotation.Keep
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * @file SurveyDao
 * @date 11/27/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/27/2023.
 */

@Keep
@Dao
interface SurveyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(surveys: List<SurveyEntity>)

    @Query("SELECT * FROM survey")
    suspend fun getAllSurveys(): List<SurveyEntity>

    @Query("DELETE FROM survey")
    suspend fun deleteAllSurveys()

}