package com.ddanddan.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestDailyCalories(
    val calorie: Int
)