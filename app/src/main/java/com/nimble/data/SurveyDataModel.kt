package com.nimble.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @file SurveyDataModel
 * @date 11/26/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/26/2023.
 */
@Keep
data class SurveyDataModel(
    @SerializedName("title") var title: String = "",
    @SerializedName("description") var description: String = "",
    @SerializedName("thank_email_above_threshold") var thankEmailAboveThreshold: String = "",
    @SerializedName("thank_email_below_threshold") var thankEmailBelowThreshold: String = "",
    @SerializedName("is_active") var isActive: Boolean = false,
    @SerializedName("cover_image_url") var coverImageUrl: String = "",
    @SerializedName("created_at") var createdAt: String = "",
    @SerializedName("active_at") var activeAt: String = "",
    @SerializedName("inactive_at") var inactiveAt: String = "",
    @SerializedName("survey_type") var surveyType: String = ""
) : Serializable