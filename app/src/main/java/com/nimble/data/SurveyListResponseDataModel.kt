package com.nimble.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @file SurveyListResponseDataModel
 * @date 11/25/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/25/2023.
 */
@Keep
data class SurveyListResponseDataModel(
    @SerializedName("data") var data: ArrayList<SurveyAttributeDataModel> = arrayListOf(),
    @SerializedName("meta") var meta: MetaDataModel = MetaDataModel()
)
