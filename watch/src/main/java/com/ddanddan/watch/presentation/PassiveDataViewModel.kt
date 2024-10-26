/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ddanddan.watch.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ddanddan.data.HealthServicesRepository
import com.ddanddan.data.PassiveDataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PassiveDataViewModel(
    private val healthServicesRepository: HealthServicesRepository,
    private val passiveDataRepository: PassiveDataRepository
) : ViewModel() {
    // Provides a hot flow of the latest calories value read from Data Store whilst there is an active
    // UI subscription. Calories values are written to the Data Store in the [PassiveDataService] each
    // time an update is provided by Health Services.
    val caloriesValue = passiveDataRepository.latestCalories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)

    // UI state to track if the feature is supported or not
    val uiState: MutableState<UiState> = mutableStateOf(UiState.Startup)

    init {
        // Check if the device supports calories capability and update the UI state
        viewModelScope.launch {
            val supported = healthServicesRepository.hasCaloriesCapability()
            uiState.value = if (supported) {
                // Device supports calories data, start collecting data
                healthServicesRepository.registerForCaloriesData()
                UiState.Supported
            } else {
                UiState.NotSupported
            }
        }
    }
}

// ViewModelFactory to create the ViewModel instance with repositories
class PassiveDataViewModelFactory(
    private val healthServicesRepository: HealthServicesRepository,
    private val passiveDataRepository: PassiveDataRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PassiveDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PassiveDataViewModel(
                healthServicesRepository = healthServicesRepository,
                passiveDataRepository = passiveDataRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// UI State to manage the supported status
sealed class UiState {
    object Startup : UiState()
    object NotSupported : UiState()
    object Supported : UiState()
}
