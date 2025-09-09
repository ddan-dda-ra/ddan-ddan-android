package com.ddanddan.ddanddan.presentation.home

import androidx.compose.runtime.Immutable
import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.entity.User
import com.ddanddan.domain.enums.PetTypeEnum
import com.ddanddan.ui.enums.TooltipType

@Immutable
data class HomeState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val pet: Pet? = null,
    val isShowTooltipState: Boolean = false,
    val tooltipType: TooltipType = TooltipType.BASIC,
    val currentTooltipMsg: String = "",
    val isPlayAndEatLottie: Boolean = false,
    val currentCalories: Double = 0.0
)

sealed class HomeSideEffect {
    data class NetworkError(val code: Int?) : HomeSideEffect()
    data class NavigateLevelUp(val level: Int, val petType: PetTypeEnum) : HomeSideEffect()
    data class NavigateNewPet(val petType: PetTypeEnum) : HomeSideEffect()
//    data class NavigatePetCollection(val petId: String) : HomeSideEffect()
    object AskNotification: HomeSideEffect()
    data class SnackBarMsg(val msg: String) : HomeSideEffect()
}