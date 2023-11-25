package com.nimble.data

import com.google.gson.annotations.SerializedName

/**
 * @file SurveysQuestionsDataModel
 * @date 11/25/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
data class SurveysQuestionsDataModel(
    @SerializedName("id") var id: String,
    @SerializedName("answers") var answers: ArrayList<SurveysAnswerDataModel> = arrayListOf()
)
