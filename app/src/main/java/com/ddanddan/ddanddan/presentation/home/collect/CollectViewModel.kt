package com.ddanddan.ddanddan.presentation.home.collect

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ddanddan.domain.usecase.GetPetListUseCase
import com.ddanddan.domain.usecase.PostMainPetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class CollectViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPetListUseCase: GetPetListUseCase,
    private val postMainPetUseCase: PostMainPetUseCase
) : ContainerHost<PetCollectionState, PetCollectionSideEffect>, ViewModel() {
    override val container =
        container<PetCollectionState, PetCollectionSideEffect>(PetCollectionState())

    fun showSnackBarEvent(msg: String) = intent {
        postSideEffect(PetCollectionSideEffect.SnackBarMsg(msg))
    }

    fun onBackButtonClicked() = intent {
        postSideEffect(PetCollectionSideEffect.NavigatePopUp)
    }

    fun changeSelectId(id: String) = intent {
        reduce {
            state.copy(mainPetId = id)
        }
    }

    fun getPetList() = intent {
        reduce {
            state.copy(isLoading = true)
        }
        getPetListUseCase()
            .onSuccess {
                reduce {
                    state.copy(pets = it, mainPetId = savedStateHandle["petId"] ?: "")
                }
            }.onFailure {
                postSideEffect(PetCollectionSideEffect.ToastNetworkError)
            }
        reduce {
            state.copy(
                isLoading = false
            )
        }
    }

    fun postMainPet() = intent {
        reduce {
            state.copy(isLoading = true)
        }
        postMainPetUseCase(state.mainPetId)
            .onSuccess {
                postSideEffect(PetCollectionSideEffect.SuccessChangePet)
            }.onFailure {
                postSideEffect(PetCollectionSideEffect.ToastNetworkError)
            }
        reduce {
            state.copy(
                isLoading = false
            )
        }
    }
}