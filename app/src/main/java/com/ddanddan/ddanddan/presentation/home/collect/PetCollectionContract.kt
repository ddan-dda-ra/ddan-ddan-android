package com.ddanddan.ddanddan.presentation.home.collect

import androidx.compose.runtime.Immutable
import com.ddanddan.domain.entity.Pet

@Immutable
data class PetCollectionState(
    val isLoading: Boolean = false,
    val pets: List<Pet> = emptyList(),
    val mainPetId: String = ""
)

sealed class PetCollectionSideEffect {
    object NavigatePopUp : PetCollectionSideEffect()
    object SuccessChangePet : PetCollectionSideEffect()
    object ToastNetworkError : PetCollectionSideEffect()
    data class SnackBarMsg(val msg: String) : PetCollectionSideEffect()
}