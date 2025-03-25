package com.ddanddan.ddanddan.presentation.setting.viewModel

import androidx.lifecycle.ViewModel
import com.ddanddan.ddanddan.presentation.setting.SettingSideEffect
import com.ddanddan.ddanddan.presentation.setting.SettingState
import com.ddanddan.domain.usecase.DeleteUserUseCase
import com.ddanddan.domain.usecase.DisableAutoLoginUseCase
import com.ddanddan.domain.usecase.GetUserInfoUseCase
import com.ddanddan.domain.usecase.PutUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val putUserInfoUseCase: PutUserInfoUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val disableAutoLoginUseCase: DisableAutoLoginUseCase
) :
    ContainerHost<SettingState, SettingSideEffect>, ViewModel() {
    override val container =
        container<SettingState, SettingSideEffect>(SettingState())

    init {
        getUserInfo()
    }


    fun incrementTarget() = intent {
        if (state.calorie < 1000) {
            reduce {
                state.copy(calorie = state.calorie + 100)
            }
        }
    }

    fun decrementTarget() = intent {
        if (state.calorie > 100) {
            reduce {
                state.copy(calorie = state.calorie - 100)
            }
        }
    }

    private fun getUserInfo() = intent {
        getUserInfoUseCase()
            .onSuccess {
                reduce {
                    state.copy(nickName = it.name ?: "", calorie = it.purposeCalorie)
                }
            }.onFailure {
                postSideEffect(SettingSideEffect.NetworkError("정보를 불러오는데 실패했습니다."))
            }
    }

    fun changeNickName(newNickName: String) = intent {
        reduce {
            state.copy(
                nickName = newNickName
            )
        }
    }

    fun updateSelection(reason: String) = intent {
        if (state.selectedReasons.contains(reason)) {
            reduce { state.copy(selectedReasons = state.selectedReasons - reason) }
        } else {
            reduce { state.copy(selectedReasons = state.selectedReasons + reason) }
        }
    }

    fun navigatePopUp() = intent {
        postSideEffect(SettingSideEffect.NavigatePopUp(state.needRefreshHomeScreen))
        reduce { state.copy(needRefreshHomeScreen = false) }
    }

    fun navigateSignOutSecond() = intent {
        postSideEffect(SettingSideEffect.NavigateSignOutSecond)
    }

    fun dismissDialog() = intent {
        reduce { state.copy(isShowLogoutDialog = false) }
    }

    fun showDialog() = intent {
        reduce { state.copy(isShowLogoutDialog = true) }
    }

    fun navigateLogin() = intent {
        dismissDialog()
        disableAutoLoginUseCase()
        postSideEffect(SettingSideEffect.NavigateLogin)
    }

    fun onSettingItemClick(titleId: Int) = intent {
        val sideEffect = when (titleId) {
            com.ddanddan.base.R.string.setting_title_text0 -> SettingSideEffect.NavigatePetCollection
            com.ddanddan.base.R.string.setting_title_text1 -> SettingSideEffect.EditNickname
            com.ddanddan.base.R.string.setting_title_text2 -> SettingSideEffect.EditTargetCalories
            com.ddanddan.base.R.string.setting_title_text4 -> SettingSideEffect.AgreeToTerms
            else -> SettingSideEffect.DeleteAccount
        }
        postSideEffect(sideEffect)
    }

    fun onEditBtnClick() = intent {
        putUserInfoUseCase(state.nickName, state.calorie)
            .onSuccess {
                reduce {
                    state.copy(
                        nickName = it.name ?: "",
                        calorie = it.purposeCalorie,
                        needRefreshHomeScreen = true
                    )
                }
                postSideEffect(SettingSideEffect.SuccessChange)
            }.onFailure {
                postSideEffect(SettingSideEffect.NetworkError("별명 변경에 실패했습니다."))
            }
    }

    fun onToggleClick() = intent {
        reduce { state.copy(isCheckBoxChecked = !state.isCheckBoxChecked) }
    }

    fun deleteUser() = intent {
        val isDeleteUser = deleteUserUseCase(state.selectedReasons.toString())
        if (isDeleteUser) postSideEffect(SettingSideEffect.NavigateOnBoarding)
        else postSideEffect(SettingSideEffect.NetworkError("회원탈퇴에 실패했습니다."))
    }

    fun onPushToggleClick() = intent {
        reduce { state.copy(isPushAllowed = !state.isPushAllowed) }
    }
}