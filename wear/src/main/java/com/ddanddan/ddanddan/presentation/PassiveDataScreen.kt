package com.ddanddan.ddanddan.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Scaffold
import com.ddanddan.ddanddan.domain.entity.MainPet
import com.ddanddan.ddanddan.domain.entity.User
import com.ddanddan.ddanddan.presentation.theme.PassiveDataTheme

@Composable
fun PassiveDataScreen(viewModel: PassiveDataViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val calories by viewModel.caloriesValue.collectAsState()
    val user by viewModel.userState.collectAsState()
    val mainPet by viewModel.mainPetState.collectAsState()

    val goalCalories = calculateGoalCalories(user)

    PassiveDataTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            RenderUI(uiState, user, mainPet, calories, goalCalories)
        }
    }
}

@Composable
fun RenderUI(
    uiState: UiState,
    user: User?,
    mainPet: MainPet?,
    calories: Double,
    goalCalories: Double
) {
    val context = LocalContext.current

    when (uiState) {
        UiState.Loading -> LoadingScreen()
        UiState.Supported -> RenderSupportedUI(user, mainPet, calories, goalCalories)
        UiState.NotSupported -> NotSupportedScreen()
        is UiState.Error -> {
            LaunchedEffect(uiState.message) {
                Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT)
                    .show() //todo - 에러 메세지를 그대로 노출하는 것이 좋아보이지 않아 추후 적절한 문구로 수정 예정
            }
        }
    }
}

@Composable
fun RenderSupportedUI(
    user: User?,
    mainPet: MainPet?,
    calories: Double,
    goalCalories: Double
) {
    if (user != null && mainPet != null) {
        CalorieProgressBar(
            calories = calories,
            goalCalories = goalCalories,
            mainPet = mainPet,
            modifier = Modifier.fillMaxSize()
        )
    } else {
        LoadingScreen()
    }
}

private fun calculateGoalCalories(user: User?): Double {
    return user?.purposeCalorie?.toDouble() ?: 1000.0
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
