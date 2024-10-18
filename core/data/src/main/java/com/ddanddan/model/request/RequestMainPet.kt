package com.ddanddan.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestMainPet(
    val petId: String
)