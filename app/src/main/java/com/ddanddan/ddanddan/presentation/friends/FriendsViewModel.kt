package com.ddanddan.ddanddan.presentation.friends

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ddanddan.domain.entity.Friend
import com.ddanddan.domain.enums.PetTypeEnum
import com.ddanddan.domain.usecase.DeleteFriendUseCase
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
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val getFriendsListUseCase: GetFriendsListUseCase,
    private val getInviteCodeUseCase: GetInviteCodeUseCase,
    private val postInviteFriendUseCase: PostInviteFriendUseCase,
    private val deleteFriendUseCase: DeleteFriendUseCase,
    private val postCheersUseCase: PostCheersUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val getMainPetUseCase: GetMainPetUseCase
) : ViewModel(), ContainerHost<FriendsState, FriendsSideEffect> {

    override val container =
        container<FriendsState, FriendsSideEffect>(FriendsState())

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
        deleteFriendUseCase(state.chosenDeleteFriendId ?: "")
            .onSuccess {
                postSideEffect(FriendsSideEffect.RefreshList)
            }
            .onFailure {
                postSideEffect(FriendsSideEffect.NetworkError("정보를 가져오는데 실패했습니다"))
            }
    }

    suspend fun getInviteCode() = intent {
        getInviteCodeUseCase()
            .onSuccess {
                reduce {
                    state.copy(myInviteLink = it)
                }
            }
            .onFailure {
                postSideEffect(FriendsSideEffect.NetworkError("정보를 가져오는데 실패했습니다"))
            }
    }

    fun copyInviteCode() = intent {
        if (state.myInviteLink == null) {
            getInviteCode()
        }
        postSideEffect(FriendsSideEffect.CopyInviteLink(state.myInviteLink ?: ""))
    }

    fun getUserDetail(uId: String) = intent {
        getUserDetailUseCase(uId)
            .onSuccess {
                reduce {
                    state.copy(
                        chosenUserDetail = it,
                        isShowProfileDialog = true
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
                    state.copy(showFireworks = true)
                }
            }
            .onFailure {
                postSideEffect(FriendsSideEffect.FailCheers)
            }
    }

    fun getMyProfile() = intent {
        getUserInfoUseCase()
            .onSuccess {
                reduce {
                    state.copy(
                        myProfile = Friend(
                            it.id,
                            it.name ?: "",
                            PetTypeEnum.DOG,
                            1
                        )
                    )
                }

                getMainPetUseCase()
                    .onSuccess {
                        reduce {
                            state.copy(
                                myProfile = Friend(
                                    state.myProfile?.id ?: "",
                                    state.myProfile?.name ?: "",
                                    it.type,
                                    it.level
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

}