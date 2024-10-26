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

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Scaffold
import com.ddanddan.data.HealthServicesRepository
import com.ddanddan.data.PassiveDataRepository
import com.ddanddan.watch.theme.PassiveDataTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PassiveDataApp(
    healthServicesRepository: HealthServicesRepository,
    passiveDataRepository: PassiveDataRepository
) {
    val permissionState = rememberPermissionState(Manifest.permission.ACTIVITY_RECOGNITION)

    PassiveDataTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            val viewModel: PassiveDataViewModel = viewModel(
                factory = PassiveDataViewModelFactory(
                    healthServicesRepository = healthServicesRepository,
                    passiveDataRepository = passiveDataRepository
                )
            )

            val calories by viewModel.caloriesValue.collectAsState()  // 실시간 칼로리 데이터 수집
            val goalCalories = 2000.0  // 목표 칼로리 설정

            Column {
                CircularProgressWithCalories(
                    calories = calories,
                    goalCalories = goalCalories
                )
            }

            // 권한 요청 처리
            if (!permissionState.status.isGranted) {
                LaunchedEffect(Unit) {
                    permissionState.launchPermissionRequest()
                }
            }
        }
    }
}