package com.ddanddan.ddanddan.presentation.home.collect

import androidx.lifecycle.ViewModel
import com.ddanddan.domain.usecase.GetMainPetUseCase
import com.ddanddan.domain.usecase.GetPetListUseCase
import com.ddanddan.domain.usecase.PostMainPetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CollectViewModel @Inject constructor(
    private val getPetListUseCase: GetPetListUseCase,
    private val postMainPetUseCase: PostMainPetUseCase,
    private val getMainPetUseCase: GetMainPetUseCase
) : ContainerHost<PetCollectionState, PetCollectionSideEffect>, ViewModel() {
    override val container =
        container<PetCollectionState, PetCollectionSideEffect>(PetCollectionState())

    fun showSnackBarEvent(msg: String, icon: Int) = intent {
        postSideEffect(PetCollectionSideEffect.SnackBarMsg(msg, icon))
    }

    fun onBackButtonClicked() = intent {
        postSideEffect(PetCollectionSideEffect.NavigatePopUp)
    }

    fun changeSelectId(id: String) = intent {
        reduce {
            state.copy(selectedPetId = id)
        }
    }

    fun getPetList() = intent {
        reduce {
            state.copy(isLoading = true)
        }
        getMainPet()
        getPetListUseCase()
            .onSuccess {
                reduce {
                    state.copy(pets = it, mainPetId = state.mainPetId)
                }
            }.onFailure {
                if (it is HttpException) {
                    postSideEffect(PetCollectionSideEffect.NetworkError(it.code()))
                } else {
                    postSideEffect(PetCollectionSideEffect.NetworkError(null))
                }            }
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
        postMainPetUseCase(state.selectedPetId)
            .onSuccess {
                postSideEffect(PetCollectionSideEffect.SuccessChangePet)
            }.onFailure {
                if (it is HttpException) {
                    postSideEffect(PetCollectionSideEffect.NetworkError(it.code()))
                } else {
                    postSideEffect(PetCollectionSideEffect.NetworkError(null))
                }
            }
        reduce {
            state.copy(
                isLoading = false
            )
        }
    }

    private suspend fun getMainPet() = intent {
        getMainPetUseCase()
            .onSuccess {
                reduce {
                    state.copy(mainPetId = it.id, selectedPetId = it.id)
                }
            }.onFailure {
                if (it is HttpException) {
                    postSideEffect(PetCollectionSideEffect.NetworkError(it.code()))
                } else {
                    postSideEffect(PetCollectionSideEffect.NetworkError(null))
                }
            }
    }
}