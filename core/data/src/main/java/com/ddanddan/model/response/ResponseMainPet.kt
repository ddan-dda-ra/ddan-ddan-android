package com.ddanddan.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseMainPet(
    val mainPet: ResponsePet
)