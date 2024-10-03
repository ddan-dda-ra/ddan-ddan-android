package com.ddanddan.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponsePets(
    val ownerUserId: String,
    val pets: List<ResponsePet>
)