package com.ddanddan.ddanddan.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.presentation.home.HomeRoute
import com.ddanddan.ddanddan.presentation.home.HomeScreen
import com.ddanddan.ddanddan.presentation.home.collect.PetCollectionRoute
import com.ddanddan.ddanddan.presentation.home.reward.LevelUpOrNetPetScreen
import com.ddanddan.ddanddan.presentation.home.reward.ToyRewardScreen
import com.ddanddan.ddanddan.presentation.navigation.DDanDDanRoute
import com.ddanddan.ddanddan.presentation.setting.SettingScreen
import com.ddanddan.ddanddan.presentation.home.collect.PetCollectionScreen
import com.ddanddan.ddanddan.presentation.setting.EditNicknameScreen

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = DDanDDanRoute.HOME.route
    ) {
        composable(DDanDDanRoute.HOME.route) {
            HomeRoute(
                onStorageClick = { petId ->
                    navController.navigate(DDanDDanRoute.PET_COLLECTION.route + "petId=${petId}")
                },
                onSettingClick = {
                    navController.navigate(DDanDDanRoute.SETTING.route)
                }
            )
        }
        composable(
            route = DDanDDanRoute.PET_COLLECTION.route + "petId={petId}",
            arguments = listOf(navArgument("petId") { type = NavType.StringType; defaultValue = "" })
        ) {
            PetCollectionRoute(
                navigatePopUp = navController::popBackStack,
                onConfirmClick = navController::popBackStack
            )
        }
        composable(DDanDDanRoute.SETTING.route) {
            SettingScreen(
                onTopBarBackClick = {
                    navController.popBackStack()
                },
                onNickNameClick = {
                    navController.navigate(DDanDDanRoute.EDIT_NICKNAME.route)
                },
                onCaloriesClick = {
                    navController.navigate(DDanDDanRoute.EDIT_TARGET.route)
                },
                onAlarmClick = {
                    //pushAlarm
                },
                onAgreeClick = {
                    //동의 Webview
                },
                onSignOutClick = {
                    //탈퇴하기
                },
                onLogOutClick = {
                    //로그아웃
                }
            )
        }
        composable(DDanDDanRoute.EDIT_NICKNAME.route) {
            EditNicknameScreen(
                onTopBarBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(DDanDDanRoute.TOY_REWARD.route) {
            ToyRewardScreen()
        }
        composable(DDanDDanRoute.LEVEL_UP.route) {
            LevelUpOrNetPetScreen(
                imageSource = R.drawable.ic_cat,
                text = "lv.2로\n업그레이드 되었어요!",
                btnText = "성장하기"
            )
        }
        composable(DDanDDanRoute.NET_PET.route) {
            LevelUpOrNetPetScreen(
                imageSource = R.drawable.ic_cat,
                text = "새로운 펫을 키울 수 있어요",
                btnText = "시작하기"
            )
        }
    }
}