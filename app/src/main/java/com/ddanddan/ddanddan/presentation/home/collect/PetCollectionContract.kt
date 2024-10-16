package com.ddanddan.ddanddan.presentation.home.collect

import com.ddanddan.domain.entity.Pet

data class PetCollectionState(
    val isLoading: Boolean = false,
    val pets: List<Pet> = emptyList(),
)

sealed class PetCollectionSideEffect {
    object NavigatePopUp : PetCollectionSideEffect()
    object ToastNetworkError : PetCollectionSideEffect()
    data class SnackBarMsg(val msg: String) : PetCollectionSideEffect()
}