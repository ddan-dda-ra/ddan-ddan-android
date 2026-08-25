package com.ddanddan.ddanddan.presentation.home.collect

import androidx.compose.runtime.Immutable
import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.entity.PetCatalog

@Immutable
data class PetCollectionState(
    val isLoading: Boolean = false,
    val pets: List<Pet> = emptyList(),
    val mainPetId: String = "",
    val selectedPetId: String = "",
    val catalog: PetCatalog? = null
)

sealed class PetCollectionSideEffect {
    object NavigatePopUp : PetCollectionSideEffect()
    object SuccessChangePet : PetCollectionSideEffect()
    data class NetworkError(val code: Int?) : PetCollectionSideEffect()
    data class SnackBarMsg(val msg: String, val icon: Int) : PetCollectionSideEffect()
}