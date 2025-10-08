package com.ddanddan.model.response

import com.ddanddan.domain.entity.Friend
import com.ddanddan.domain.enums.PetTypeEnum
import kotlinx.serialization.Serializable

@Serializable
data class ResponseFriend(
    val id: String,
    val name: String,
    val mainPetType: String,
    val petLevel: Int
) {
    fun toFriend(): Friend {
        return Friend(id, name, PetTypeEnum.valueOf(mainPetType), petLevel)
    }
}