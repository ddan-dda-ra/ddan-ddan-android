package com.ddanddan.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseInviterUser (
    val inviterUser: ResponseFriend
)