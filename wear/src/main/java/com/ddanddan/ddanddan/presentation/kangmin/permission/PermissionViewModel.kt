package com.ddanddan.ddanddan.presentation.kangmin.permission

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.ddanddan.ddanddan.domain.repository.HealthServicesRepository
import com.ddanddan.ddanddan.presentation.kangmin.PassiveDataService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(
    private val healthServicesRepository: HealthServicesRepository,
    @ApplicationContext private val context: Context
) : ContainerHost<PermissionState, PermissionSideEffect>, ViewModel() {
    override val container =
        container<PermissionState, PermissionSideEffect>(PermissionState())

    fun checkCalorieSupportAndRegister() = intent {
        val isCalorieSupported = healthServicesRepository.hasCaloriesCapability()
        if (isCalorieSupported) {
            try {
                // 1. Health Services에 리스너 등록
                healthServicesRepository.registerForCaloriesData()
                
                // 2. PassiveDataService 시작
                val serviceIntent = Intent(context, PassiveDataService::class.java)
                context.startForegroundService(serviceIntent)
                
                postSideEffect(PermissionSideEffect.NavigateCalories)
            } catch (e: Exception) {
                postSideEffect(PermissionSideEffect.NavigateNotSupportCalories)
            }
        } else {
            postSideEffect(PermissionSideEffect.NavigateNotSupportCalories)
        }
    }
}