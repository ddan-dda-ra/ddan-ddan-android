package com.ddanddan.watch.presentation

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Scaffold
import com.ddanddan.watch.theme.PassiveDataTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PassiveDataApp() {
    val permissionState = rememberPermissionState(Manifest.permission.ACTIVITY_RECOGNITION)

    PassiveDataTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            val viewModel: PassiveDataViewModel = hiltViewModel()

            val calories by viewModel.caloriesValue.collectAsState()  // 실시간 칼로리 데이터 수집
            val goalCalories = 2000.0  //목표 칼로리 설정

            Column {
                CalorieProgressBar(
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
