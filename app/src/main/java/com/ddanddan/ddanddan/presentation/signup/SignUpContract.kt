package com.ddanddan.ddanddan.presentation.signup

import com.ddanddan.domain.entity.Pet

data class SignUpState(
    val isLoading: Boolean = false,
    val newPet: Pet? = null,
    val signUpSuccess: Boolean = false
)

sealed class SignUpSideEffect {
    object ToastNetworkError : SignUpSideEffect()
    data class SnackBarMsg(val msg: String) : SignUpSideEffect()
}