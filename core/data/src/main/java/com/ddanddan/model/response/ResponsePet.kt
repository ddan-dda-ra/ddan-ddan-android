package com.ddanddan.model.response

import com.ddanddan.domain.entity.Pet
import kotlinx.serialization.Serializable

/**
 * @property id Pet 식별 ID
 * @property type Pet 종류
 * @property level Pet 레벨
 * @property expPercent 현재 레벨에 해당하는 경험치 퍼센트
 */
@Serializable
data class ResponsePet(
    val id: String,
    val type: String,
    val level: Int,
    val expPercent: Double
) {
    fun toPet() = Pet(id, type, level, expPercent)
}