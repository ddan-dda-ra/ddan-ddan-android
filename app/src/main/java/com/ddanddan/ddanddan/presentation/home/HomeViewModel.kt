package com.ddanddan.ddanddan.presentation.home

import androidx.lifecycle.ViewModel
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
    private val postPlayPetUseCase: PostPlayPetUseCase,
    private val postFoodPetUseCase: PostFoodPetUseCase
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {
    override val container =
        container<HomeState, HomeSideEffect>(HomeState())

    fun postPlayPet(petId: String) = intent {
        postPlayPetUseCase(petId)
            .onSuccess {
                reduce {
                    state.copy(userPet = it)
                }
            }.onFailure {
                postSideEffect(HomeSideEffect.ToastNetworkError)
            }
    }

    fun postFoodPet(petId: String) = intent {
        postFoodPetUseCase(petId)
            .onSuccess {
                reduce {
                    state.copy(userPet = it)
                }
            }.onFailure {
                postSideEffect(HomeSideEffect.ToastNetworkError)
            }
    }
}