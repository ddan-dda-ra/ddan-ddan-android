package com.ddanddan.model.response

import com.ddanddan.domain.entity.UserDetail
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserDetail(
    val userId: String,
    val userName: String,
    val mainPet: ResponsePet,
    val todayCalorie: Int,
    val monthlyReceivedCheerCount: Int,
    val isFriend: Boolean,
    val isCheeredToday: Boolean
) {
    fun toUserDetail(): UserDetail = UserDetail(userId, userName, mainPet.toPet(), todayCalorie, monthlyReceivedCheerCount, isFriend, isCheeredToday)
}