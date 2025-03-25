package com.ddanddan.domain.entity

import com.ddanddan.domain.enums.PetTypeEnum

data class Rank(
    val rank: Int,
    val userId: String,
    val userName: String,
    val mainPetType: PetTypeEnum,
    val petLevel: Int,
    val totalCalories: Int,
    val totalSucceededDays: Int
)