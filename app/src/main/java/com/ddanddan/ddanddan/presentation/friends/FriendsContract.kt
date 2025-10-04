package com.ddanddan.ddanddan.presentation.friends

import androidx.compose.runtime.Immutable
import com.ddanddan.domain.entity.Friend

@Immutable
data class FriendsState(
    val myProfile: Friend? = null,
    val friends: List<Friend> = listOf()
)

sealed class FriendsSideEffect {
    data class NetworkError(val msg: String): FriendsSideEffect()
}