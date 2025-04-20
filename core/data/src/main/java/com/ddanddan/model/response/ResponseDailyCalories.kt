package com.ddanddan.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDailyCalories(
    val user: ResponseUser,
    val dailyInfo: ResponseDailyInfo,
    val rewardedFoodQuantity: Int,
    val rewardedToyQuantity: Int
)