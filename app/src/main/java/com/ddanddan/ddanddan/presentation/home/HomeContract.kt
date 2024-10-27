package com.ddanddan.ddanddan.presentation.home

import androidx.compose.runtime.Immutable
import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.entity.User
import com.ddanddan.domain.enums.PetTypeEnum

@Immutable
data class HomeState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val pet: Pet? = null
)

sealed class HomeSideEffect {
    object NavigateSetting: HomeSideEffect()
    data class NetworkError(val code: Int?) : HomeSideEffect()
    data class NavigateLevelUp(val level: Int, val petType: PetTypeEnum) : HomeSideEffect()
    data class NavigateNewPet(val petType: PetTypeEnum) : HomeSideEffect()
    data class NavigatePetCollection(val petId: String) : HomeSideEffect()
    data class SnackBarMsg(val msg: String) : HomeSideEffect()
}