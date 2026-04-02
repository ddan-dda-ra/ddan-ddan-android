package com.ddanddan.ddanddan.presentation.friends

import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.chottulink.lib.ChottuLink
import com.chottulink.lib.DynamicLink
import com.ddanddan.ddanddan.util.toBaseErrorResponse
import com.ddanddan.domain.entity.Friend
import com.ddanddan.domain.usecase.DeleteFriendUseCase
import com.ddanddan.domain.usecase.GetFriendByCodeUseCase
import com.ddanddan.domain.usecase.GetFriendsListUseCase
import com.ddanddan.domain.usecase.GetInviteCodeUseCase
import com.ddanddan.domain.usecase.GetMainPetUseCase
import com.ddanddan.domain.usecase.GetUserDetailUseCase
import com.ddanddan.domain.usecase.GetUserInfoUseCase
import com.ddanddan.domain.usecase.PostCheersUseCase
import com.ddanddan.domain.usecase.PostInviteFriendUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getFriendsListUseCase: GetFriendsListUseCase,
    private val getInviteCodeUseCase: GetInviteCodeUseCase,
    private val postInviteFriendUseCase: PostInviteFriendUseCase,
    private val deleteFriendUseCase: DeleteFriendUseCase,
    private val postCheersUseCase: PostCheersUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val getMainPetUseCase: GetMainPetUseCase,
    private val getFriendByCodeUseCase: GetFriendByCodeUseCase
) : ViewModel(), ContainerHost<FriendsState, FriendsSideEffect> {

    override val container =
        container<FriendsState, FriendsSideEffect>(FriendsState())

    private val inviteCode: String? = savedStateHandle["inviteCode"]

    init {
        inviteCode?.let {
            getFriendByCode(it)
        }
    }

    private fun getFriendByCode(code: String) = intent {
        getFriendByCodeUseCase(code)
            .onSuccess { inviterUser ->
                reduce { state.copy(pendingInviteCode = code) }
                getUserDetail(inviterUser.id)
            }.onFailure {
                postSideEffect(FriendsSideEffect.NetworkError("초대 정보를 불러오지 못했어요"))
            }
    }

    fun getFriendsList() = intent {
        getFriendsListUseCase()
            .onSuccess { list ->
                reduce {
                    state.copy(
                        friends = list
                    )
                }
            }
            .onFailure {
                postSideEffect(FriendsSideEffect.NetworkError("정보를 가져오는데 실패했습니다"))
            }
    }

    fun chooseDeleteFriend(fId: String) = intent {
        reduce {
            state.copy(
                chosenDeleteFriendId = fId,
                isShowDeleteDialog = true
            )
        }
    }

    fun dismissDialog() = intent {
        reduce { state.copy(isShowDeleteDialog = false) }
    }

    fun deleteFriend() = intent {
        val friendId = state.chosenDeleteFriendId ?: run {
            postSideEffect(FriendsSideEffect.NetworkError("삭제할 친구를 선택해주세요"))
            return@intent
        }
        deleteFriendUseCase(friendId)
            .onSuccess {
                reduce {
                    state.copy(isShowDeleteDialog = false)
                }
                postSideEffect(FriendsSideEffect.ShowDeleteFriendsSnackBar)
                postSideEffect(FriendsSideEffect.RefreshList)
            }
            .onFailure {
                postSideEffect(FriendsSideEffect.NetworkError("정보를 가져오는데 실패했습니다"))
            }
    }

    fun copyInviteCode() = intent {
            getInviteCodeUseCase()
                .onSuccess {
                    val shortUrl = createInviteLink(it)
                    shortUrl?.let { code ->
                        postSideEffect(FriendsSideEffect.CopyInviteLink(code))
                    } ?: run {
                        postSideEffect(FriendsSideEffect.NetworkError("링크를 불러오지 못했어요. 다시 시도해주세요."))
                    }
                }
                .onFailure {
                    postSideEffect(FriendsSideEffect.NetworkError("정보를 가져오는데 실패했습니다"))
                    return@intent
                }
    }

    private suspend fun createInviteLink(code: String): String? {
        return suspendCoroutine<String?> { continuation ->
            ChottuLink.createDynamicLink()
                .setLink((("http://ddanddan.chottu.link?code=$code").toUri()))
                .setDomain("ddanddan.chottu.link")
                .setLinkName("friend-invite")
                .androidBehavior(DynamicLink.BEHAVIOR_APP)
                .build()
                .addOnSuccessListener {
                    if (it != null && it.uri != null) {
                        continuation.resume(it.uri.toString())
                    } else {
                        continuation.resume(null)
                    }
                }.addOnFailureListener {
                    continuation.resume(null)
                }
        }
    }

    fun getUserDetail(uId: String) = intent {
        getUserDetailUseCase(uId)
            .onSuccess {
                reduce {
                    state.copy(
                        chosenUserDetail = it,
                        isShowProfileDialog = true,
                        isCheeredToday = it.isCheeredToday
                    )
                }
            }
            .onFailure {
                postSideEffect(FriendsSideEffect.NetworkError("정보를 가져오는데 실패했습니다"))
            }
    }

    fun dismissDetailDialog() = intent {
        reduce {
            state.copy(
                isShowProfileDialog = false,
                showFireworks = false
            )
        }
    }

    fun postCheers(uId: String) = intent {
        postCheersUseCase(uId)
            .onSuccess {
                // 불꽃 애니메이션
                reduce {
                    state.copy(
                        showFireworks = true,
                        isCheeredToday = true
                    )
                }
            }
            .onFailure {
                if (it is HttpException) {
                    it.toBaseErrorResponse()?.let { error ->
                        postSideEffect(FriendsSideEffect.NetworkError(error.message))

                    }
                }
            }
    }

    fun getMyProfile() = intent {
        getUserInfoUseCase()
            .onSuccess { userInfo ->
                getMainPetUseCase()
                    .onSuccess { mainPet ->
                        reduce {
                            state.copy(
                                myProfile = Friend(
                                    userInfo.id,
                                    userInfo.name ?: "",
                                    mainPet.type,
                                    mainPet.level
                                )
                            )
                        }
                    }
                    .onFailure {
                        postSideEffect(FriendsSideEffect.NetworkError("정보를 가져오는데 실패했습니다"))
                    }
            }
            .onFailure {
                postSideEffect(FriendsSideEffect.NetworkError("정보를 가져오는데 실패했습니다"))
            }
    }

    fun postInviteFriend(code: String) = intent {
        postInviteFriendUseCase(code)
            .onSuccess {
                postSideEffect(FriendsSideEffect.NavigateAddedFriend(it.mainPetType, it.petLevel))
            }.onFailure {
                postSideEffect(FriendsSideEffect.NetworkError("친구 추가에 실패했습니다"))
            }
    }

    fun clearPendingInviteCode() = intent {
        reduce { state.copy(pendingInviteCode = null) }
    }
}