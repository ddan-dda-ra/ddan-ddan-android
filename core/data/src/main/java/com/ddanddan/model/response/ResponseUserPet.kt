package com.ddanddan.model.response

import com.ddanddan.domain.entity.UserPet
import kotlinx.serialization.Serializable

/**
 * @property user User 정보
 * @property pet Pet 정보
 */
@Serializable
data class ResponseUserPet(
    val user: ResponseUser,
    val pet: ResponsePet
) {
    fun toUserPet() = UserPet(user.toUser(), pet.toPet())
}