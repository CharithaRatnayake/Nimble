package com.nimble.data

import com.google.gson.annotations.SerializedName

/**
 * @file SurveysDataModel
 * @date 11/25/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
data class SurveysDataModel(
    @SerializedName("survey_id") var surveyId: String,
    @SerializedName("questions") var questions: ArrayList<SurveysQuestionsDataModel> = arrayListOf()
)
