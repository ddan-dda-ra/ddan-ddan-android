package com.ddanddan.ddanddan.presentation.home

import androidx.compose.runtime.Immutable
import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.entity.User
import com.ddanddan.domain.enums.PetTypeEnum
import com.ddanddan.ui.enums.TooltipType

@Immutable
data class HomeState(
    val isLoading: Boolean = true,
    val user: User? = null,
    val pet: Pet? = null,
    val newPet: Pet? = null,
    // 첫 알뽑기 코치마크
    val firstEggCountBadge: Boolean = false,
    // 0개일때 알뽑기 툴팁
    val isShowEggZeroTooltip: Boolean = false,
    val isShowTooltipState: Boolean = false,
    val tooltipType: TooltipType = TooltipType.BASIC,
    val currentTooltipMsg: String = "",
    val isPlayAndEatLottie: Boolean = false,
    val currentCalories: Double = 0.0,
    // 알 뽑기 애니메이션
    val isShowingEggAnimation: Boolean = false,
    val isShowGuideline: Boolean = false,
    val isShowPermissionDialog: Boolean = false
)

sealed class HomeSideEffect {
    data class NetworkError(val code: Int?) : HomeSideEffect()
    data class NavigateLevelUp(val level: Int, val petType: PetTypeEnum) : HomeSideEffect()
    data object AskNotification : HomeSideEffect()
    data class SnackBarMsg(val msg: String) : HomeSideEffect()
}