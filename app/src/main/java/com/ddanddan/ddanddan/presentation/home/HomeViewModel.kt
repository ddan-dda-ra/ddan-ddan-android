package com.ddanddan.ddanddan.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ddanddan.ddanddan.presentation.home.collect.PetCollectionSideEffect
import com.ddanddan.domain.usecase.GetMainPetUseCase
import com.ddanddan.domain.usecase.GetUserInfoUseCase
import com.ddanddan.domain.usecase.PostFoodPetUseCase
import com.ddanddan.domain.usecase.PostPlayPetUseCase
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
    private val postFoodPetUseCase: PostFoodPetUseCase
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
        state.pet?.let {
            if ((state.user?.foodQuantity ?: 0) > 0) {
                postFoodPetUseCase(it.id)
                    .onSuccess {
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

    fun showSnackBarEvent(msg: String) = intent {
        postSideEffect(HomeSideEffect.SnackBarMsg(msg))
    }
}