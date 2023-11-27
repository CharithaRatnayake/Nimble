package com.nimble.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @file SurveyEntity
 * @date 11/27/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 11/27/2023.
 */
@Entity(tableName = "survey", indices = [Index(value = ["id"], unique = true)])
data class SurveyEntity(
    @PrimaryKey()
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var coverImageUrl: String = ""
)
