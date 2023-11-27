package com.nimble.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @file SurveyAttributeDataModel
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
data class SurveyAttributeDataModel(
    @SerializedName("id") var id: String = "",
    @SerializedName("type") var type: String = "",
    @SerializedName("attributes") var attributes: SurveyDataModel = SurveyDataModel()
) : Serializable