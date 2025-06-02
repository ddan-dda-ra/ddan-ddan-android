package com.ddanddan.ddanddan.presentation.kangmin.permission

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ddanddan.ddanddan.domain.repository.HealthServicesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(
    private val healthServicesRepository: HealthServicesRepository
) : ContainerHost<PermissionState, PermissionSideEffect>, ViewModel() {
    override val container =
        container<PermissionState, PermissionSideEffect>(PermissionState())

    fun checkCalorieSupportAndRegister() = intent {
        val isCalorieSupported = healthServicesRepository.hasCaloriesCapability()
        Log.d("kangmi", isCalorieSupported.toString())
        if (isCalorieSupported) {
            healthServicesRepository.registerForCaloriesData()
            postSideEffect(PermissionSideEffect.NavigateCalories)
        } else {
            postSideEffect(PermissionSideEffect.NavigateNotSupportCalories)
        }
    }
}