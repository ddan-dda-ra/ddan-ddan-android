package com.ddanddan.ddanddan.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ddanddan.ddanddan.presentation.home.collect.PetCollectionSideEffect
import com.ddanddan.domain.usecase.GetMainPetUseCase
import com.ddanddan.domain.usecase.GetUserInfoUseCase
import com.ddanddan.domain.usecase.PostFoodPetUseCase
import com.ddanddan.domain.usecase.PostPlayPetUseCase
import com.ddanddan.domain.usecase.PostRandomPetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getMainPetUseCase: GetMainPetUseCase,
    private val postPlayPetUseCase: PostPlayPetUseCase,
    private val postFoodPetUseCase: PostFoodPetUseCase,
    private val postRandomPetUseCase: PostRandomPetUseCase
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {
    override val container =
        container<HomeState, HomeSideEffect>(HomeState())

    init {
        getUserInfo()
        getMainPet()
    }

    private fun getUserInfo() = intent {
        getUserInfoUseCase()
            .onSuccess {
                reduce {
                    state.copy(user = it)
                }
            }
    }

    private fun getMainPet() = intent {
        getMainPetUseCase()
            .onSuccess {
                reduce {
                    state.copy(pet = it)
                }
            }
    }

    fun postPlayPet() = intent {
        state.pet?.let {
            if ((state.user?.toyQuantity ?: 0) > 0) {
                postPlayPetUseCase(it.id)
                    .onSuccess {
                        reduce {
                            state.copy(user = it.user, pet = it.pet)
                        }
                    }.onFailure {
                        postSideEffect(HomeSideEffect.ToastNetworkError)
                    }
            } else {
                postSideEffect(HomeSideEffect.SnackBarMsg("놀아주기 개수가 부족합니다."))
            }
        } ?: run {
            postSideEffect(HomeSideEffect.SnackBarMsg("펫 아이디에 오류가 발생했습니다."))
        }
    }

    fun postFoodPet() = intent {
        state.pet?.let { pet ->
            if ((state.user?.foodQuantity ?: 0) > 0) {
                postFoodPetUseCase(pet.id)
                    .onSuccess {
                        if (it.pet.level == MAX_LEVEL && it.pet.expPercent == MAX_PERCENTS) {
                            postRandomPet()
                        } else if (it.pet.level > (state.pet?.level ?: 0)) {
                            postSideEffect(
                                HomeSideEffect.NavigateLevelUp(
                                    it.pet.level,
                                    it.pet.type
                                )
                            )
                        }
                        reduce {
                            state.copy(user = it.user, pet = it.pet)
                        }
                    }.onFailure {
                        postSideEffect(HomeSideEffect.ToastNetworkError)
                    }
            } else {
                postSideEffect(HomeSideEffect.SnackBarMsg("먹이주기 개수가 부족합니다."))
            }
        } ?: run {
            postSideEffect(HomeSideEffect.SnackBarMsg("펫 아이디에 오류가 발생했습니다."))
        }
    }

    fun onStorageClick() = intent {
        postSideEffect(HomeSideEffect.NavigatePetCollection(state.pet?.id ?: ""))
    }

    fun onSettingClick() = intent {
        postSideEffect(HomeSideEffect.NavigateSetting)
    }

    private fun postRandomPet() = intent {
        postRandomPetUseCase()
            .onSuccess {
                reduce {
                    state.copy(pet = it)
                }
                postSideEffect(HomeSideEffect.NavigateNewPet(it.type))
            }.onFailure {
                postSideEffect(HomeSideEffect.SnackBarMsg("새로운 펫을 불러오는데 오류가 발생했습니다."))
            }
    }

    companion object {
        private const val MAX_LEVEL = 5
        private const val MAX_PERCENTS = 100.0
    }
}