package com.ddanddan.model.response

import com.ddanddan.domain.entity.Friend
import kotlinx.serialization.Serializable

@Serializable
data class ResponseFriends(
    val friends: List<ResponseFriend>,
    val totalCount: Int
)