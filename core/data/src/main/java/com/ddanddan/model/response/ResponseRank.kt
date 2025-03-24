package com.ddanddan.model.response

import com.ddanddan.domain.entity.Rank
import com.ddanddan.domain.enums.PetTypeEnum
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRank(
    val rank: Int,
    val userId: String,
    val userName: String,
    val mainPetType: PetTypeEnum,
    val petLevel: Int,
    val totalCalories: Int,
    val totalSucceededDays: Int
) {
    fun toRank() = Rank(rank, userName, mainPetType, petLevel, totalCalories, totalSucceededDays)
}