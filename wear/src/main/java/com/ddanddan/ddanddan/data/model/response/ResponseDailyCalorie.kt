package com.ddanddan.ddanddan.data.model.response

import com.ddanddan.ddanddan.domain.entity.UserDailyInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDailyCalorie(
    @SerialName("dailyInfo")
    val dailyInfo: DailyInfo? = null,
    @SerialName("user")
    val user: User? = null
) {
    @Serializable
    data class DailyInfo(
        @SerialName("calorie")
        val calorie: Int? = null,
        @SerialName("date")
        val date: String? = null,
        @SerialName("id")
        val id: String? = null,
        @SerialName("userId")
        val userId: String? = null
    )

    @Serializable
    data class User(
        @SerialName("foodQuantity")
        val foodQuantity: Int? = null,
        @SerialName("id")
        val id: String? = null,
        @SerialName("name")
        val name: String? = null,
        @SerialName("purposeCalorie")
        val purposeCalorie: Int? = null,
        @SerialName("toyQuantity")
        val toyQuantity: Int? = null
    )
}

//todo - null 시 대체 값 문제될 여지가 없는지 확인 필요
fun ResponseDailyCalorie.toUserDailyInfo(): UserDailyInfo =
    UserDailyInfo(
        dailyInfo = dailyInfo?.toDailyInfo() ?: UserDailyInfo.DailyInfo(
            calorie = 0,
            date = "",
            id = "",
            userId = ""
        ),
        user = user?.toUser() ?: UserDailyInfo.User(
            foodQuantity = 0,
            id = "",
            name = "",
            purposeCalorie = 0,
            toyQuantity = 0
        )
    )

fun ResponseDailyCalorie.DailyInfo.toDailyInfo(): UserDailyInfo.DailyInfo =
    UserDailyInfo.DailyInfo(
        calorie = calorie ?: 0,
        date = date ?: "",
        id = id ?: "",
        userId = userId ?: ""
    )

fun ResponseDailyCalorie.User.toUser(): UserDailyInfo.User =
    UserDailyInfo.User(
        foodQuantity = foodQuantity ?: 0,
        id = id ?: "",
        name = name ?: "",
        purposeCalorie = purposeCalorie ?: 0,
        toyQuantity = toyQuantity ?: 0
    )