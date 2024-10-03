package com.ddanddan.model.response

import com.ddanddan.domain.entity.Play
import kotlinx.serialization.Serializable

/**
 * @property user User 정보
 * @property pet Pet 정보
 */
@Serializable
data class ResponsePlay(
    val user: ResponseUser,
    val pet: ResponsePet
) {
    fun toPlay() = Play(user.toUser(), pet.toPet())
}