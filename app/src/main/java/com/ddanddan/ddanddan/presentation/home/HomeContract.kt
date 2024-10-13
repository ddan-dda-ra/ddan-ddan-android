package com.ddanddan.ddanddan.presentation.home

import com.ddanddan.domain.entity.UserPet

data class HomeState(
    val isLoading: Boolean = false,
    val userPet: UserPet? = null
)

sealed class HomeSideEffect {
    object ToastNetworkError : HomeSideEffect()
    data class SnackBarMsg(val msg: String) : HomeSideEffect()
}