package com.ddanddan.ddanddan.presentation.signup

import com.ddanddan.domain.enums.PetTypeEnum

data class SignUpState(
    val nickname: String = "",
    val isValidNickname: Boolean = false,
    val isNickNameLengthOver: Boolean = false,
    val calorie: Int = 300,
    val petType: PetTypeEnum? = null
)

sealed class SignUpSideEffect {
    object SuccessSignUp: SignUpSideEffect()
    data class NetworkError(val msg: String): SignUpSideEffect()

    object NavigateTerms: SignUpSideEffect()
    object NavigateNickname: SignUpSideEffect()
    object NavigateTargetCalories: SignUpSideEffect()
    object NavigateFirstEgg: SignUpSideEffect()
}