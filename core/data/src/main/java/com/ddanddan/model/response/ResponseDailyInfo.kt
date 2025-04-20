package com.ddanddan.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDailyInfo(
    val id: String,
    val userId: String,
    val date: String,
    val calorie: Int
)