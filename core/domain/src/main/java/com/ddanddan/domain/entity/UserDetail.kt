package com.ddanddan.domain.entity

data class UserDetail(
    val id: String,
    val name: String,
    val mainPet: Pet,
    val todayCalorie: Int,
    val monthlyReceivedCheerCount: Int,
    val isFriend: Boolean,
    val isCheeredToday: Boolean
)