package com.ddanddan.ddanddan.presentation.home

import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.entity.User

data class HomeState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val pet: Pet? = null
)

sealed class HomeSideEffect {
    object ToastNetworkError : HomeSideEffect()
    data class SnackBarMsg(val msg: String) : HomeSideEffect()
}