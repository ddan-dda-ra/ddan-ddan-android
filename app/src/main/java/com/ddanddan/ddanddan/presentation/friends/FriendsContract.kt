package com.ddanddan.ddanddan.presentation.friends

import androidx.compose.runtime.Immutable
import com.ddanddan.domain.entity.Friend
import com.ddanddan.domain.entity.UserDetail

@Immutable
data class FriendsState(
    val myProfile: Friend? = null,
    val friends: List<Friend> = listOf(),
    val myInviteLink: String? = null,
    val isShowDeleteDialog: Boolean = false,
    val chosenDeleteFriendId: String? = null,
    val isShowProfileDialog: Boolean = false,
    val chosenUserDetail: UserDetail? = null,
    val showFireworks: Boolean = false
)

sealed class FriendsSideEffect {
    object RefreshList : FriendsSideEffect()
    data class CopyInviteLink(val link: String) : FriendsSideEffect()
    object FailCheers : FriendsSideEffect()
    data class NetworkError(val msg: String): FriendsSideEffect()
}