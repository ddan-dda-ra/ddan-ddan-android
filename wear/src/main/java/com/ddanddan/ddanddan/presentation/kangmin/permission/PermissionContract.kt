package com.ddanddan.ddanddan.presentation.kangmin.permission

import javax.annotation.concurrent.Immutable

@Immutable
data class PermissionState(
    val isLoading: Boolean = false
)

sealed class PermissionSideEffect {
    object NavigateCalories : PermissionSideEffect()
    object NavigateNotSupportCalories : PermissionSideEffect()
}