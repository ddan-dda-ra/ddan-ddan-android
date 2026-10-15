package com.ddanddan.domain.entity

data class Rank(
    val rank: Int,
    val userId: String,
    val userName: String,
    val mainPetType: String,
    val petLevel: Int,
    val totalCalories: Int,
    val totalSucceededDays: Int
)