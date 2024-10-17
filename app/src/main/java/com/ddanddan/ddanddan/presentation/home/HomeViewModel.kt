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

    fun postPlayPet(petId: String) = intent {
        postPlayPetUseCase(petId)
            .onSuccess {
                reduce {
                    state.copy(user = it.user, pet = it.pet)
                }
            }.onFailure {
                postSideEffect(HomeSideEffect.ToastNetworkError)
            }
    }

    fun postFoodPet(petId: String) = intent {
        postFoodPetUseCase(petId)
            .onSuccess {
                reduce {
                    state.copy(user = it.user, pet = it.pet)
                }
            }.onFailure {
                postSideEffect(HomeSideEffect.ToastNetworkError)
            }
    }

    fun showSnackBarEvent(msg: String) = intent {
        postSideEffect(HomeSideEffect.SnackBarMsg(msg))
    }
}