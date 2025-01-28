package com.ddanddan.ddanddan.presentation.signin

import androidx.compose.runtime.Immutable

@Immutable
data class SignInState(
    val isShowProgressBar: Boolean = false,
    val kakaoToken: String = ""
)

sealed class SignInSideEffect {
    data class SuccessLogin(val accessToken: String, val refreshToken: String) : SignInSideEffect()
    object UserNotRegistered : SignInSideEffect()
    data class NetworkError(val msg: String) : SignInSideEffect()
}