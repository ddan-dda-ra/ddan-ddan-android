package com.ddanddan.ddanddan.presentation.friends

import androidx.compose.runtime.Immutable
import com.ddanddan.domain.entity.Friend
import com.ddanddan.domain.entity.UserDetail
import com.ddanddan.domain.enums.PetTypeEnum

@Immutable
data class FriendsState(
    val myProfile: Friend? = null,
    val friends: List<Friend> = listOf(),
    val isShowDeleteDialog: Boolean = false,
    val chosenDeleteFriendId: String? = null,
    val isShowProfileDialog: Boolean = false,
    val chosenUserDetail: UserDetail? = null,
    val showFireworks: Boolean = false,
    val isCheeredToday: Boolean = false
)

sealed class FriendsSideEffect {
    object RefreshList : FriendsSideEffect()
    data class NavigateAddedFriend(val type: PetTypeEnum, val level: Int) : FriendsSideEffect()
    data class CopyInviteLink(val link: String) : FriendsSideEffect()
    object FailCheers : FriendsSideEffect()
    data class NetworkError(val msg: String): FriendsSideEffect()
}