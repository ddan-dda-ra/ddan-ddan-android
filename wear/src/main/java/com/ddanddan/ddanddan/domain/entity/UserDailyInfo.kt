package com.ddanddan.ddanddan.domain.entity

data class UserDailyInfo(
    val dailyInfo: DailyInfo,
    val user: User
) {
    data class DailyInfo(
        val calorie: Int,
        val date: String,
        val id: String,
        val userId: String
    )

    data class User(
        val foodQuantity: Int,
        val id: String,
        val name: String,
        val purposeCalorie: Int,
        val toyQuantity: Int
    )
}
