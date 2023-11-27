package com.nimble.di.repository

import com.nimble.data.local.SurveyDao
import com.nimble.data.local.SurveyEntity
import javax.inject.Inject

/**
 * @file LocalAppRepository
 * @date 11/27/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/27/2023.
 */
class LocalAppRepository @Inject constructor(
    private val surveyDao: SurveyDao
) {

    suspend fun insertSurveys(surveys: List<SurveyEntity>) {
        surveyDao.insertAll(surveys)
    }

    suspend fun getAllSurveys(): List<SurveyEntity> {
        return surveyDao.getAllSurveys()
    }

}