package com.ddanddan.model.response

import com.ddanddan.domain.entity.User
import kotlinx.serialization.Serializable

/**
 * @property id User 식별 ID
 * @property name User 표기 이름
 * @property purposeCalorie User 목표 칼로리
 * @property foodQuantity User 음식 수량
 * @property toyQuantity User 장난감 수량
 */
@Serializable
data class ResponseUser(
    val id: String,
    val name: String?,
    val purposeCalorie: Int,
    val foodQuantity: Int,
    val toyQuantity: Int
) {
    fun toUser() = User(id, name, purposeCalorie, foodQuantity, toyQuantity)
}