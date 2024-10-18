package com.ddanddan.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestTypePet (
    val petType: String
)