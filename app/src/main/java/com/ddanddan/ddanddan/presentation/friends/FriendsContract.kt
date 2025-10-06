package com.ddanddan.ddanddan.presentation.friends

import androidx.compose.runtime.Immutable
import com.ddanddan.domain.entity.Friend

@Immutable
data class FriendsState(
    val myProfile: Friend? = null,
    val friends: List<Friend> = listOf(),
    val myInviteLink: String? = null,
    val isShowDeleteDialog: Boolean = false,
    val chosenFriendId: String? = null
)

sealed class FriendsSideEffect {
    object RefreshList : FriendsSideEffect()
    data class CopyInviteLink(val link: String) : FriendsSideEffect()
    data class NetworkError(val msg: String): FriendsSideEffect()
}