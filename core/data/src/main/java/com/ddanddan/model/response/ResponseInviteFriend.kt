package com.ddanddan.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseInviteFriend(
    val id: String,
    val friendUser: ResponseFriend,
    val createdAt: String,
    val acceptedAt: String
)