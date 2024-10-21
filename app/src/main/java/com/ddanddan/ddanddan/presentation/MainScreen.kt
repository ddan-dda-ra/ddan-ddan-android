package com.ddanddan.ddanddan.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ddanddan.ddanddan.presentation.home.HomeRoute
import com.ddanddan.ddanddan.presentation.home.collect.PetCollectionRoute
import com.ddanddan.ddanddan.presentation.home.reward.ToyRewardScreen
import com.ddanddan.ddanddan.presentation.home.reward.level.LevelUpRoute
import com.ddanddan.ddanddan.presentation.home.reward.pet.NewPetRoute
import com.ddanddan.ddanddan.presentation.navigation.DDanDDanRoute
import com.ddanddan.ddanddan.presentation.setting.EditNicknameScreen
import com.ddanddan.ddanddan.presentation.setting.SettingScreen
import com.ddanddan.domain.enum.PetTypeEnum

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
                    navController.navigate(DDanDDanRoute.PET_COLLECTION.route + "?petId=${petId}")
                },
                onSettingClick = {
                    navController.navigate(DDanDDanRoute.SETTING.route)
                },
                onNavigateLevelUp = { level, petType ->
                    navController.navigate(DDanDDanRoute.LEVEL_UP.route + "?level=${level}&petType=${petType}")
                },
                onNavigateNewPet = { petType ->
                    navController.navigate(DDanDDanRoute.NET_PET.route + "?petType=${petType}")
                }
            )
        }
        composable(
            route = DDanDDanRoute.PET_COLLECTION.route + "?petId={petId}",
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
        composable(
            route = DDanDDanRoute.LEVEL_UP.route + "?level={level}" +"&petType={petType}",
            arguments = listOf(navArgument("level") { type = NavType.IntType; defaultValue = 0 }, navArgument("petType") { type = NavType.StringType; defaultValue = PetTypeEnum.CAT.name })
        ) {
            val petTypeString = it.arguments?.getString("petType") ?: PetTypeEnum.CAT.name
            val petType = PetTypeEnum.valueOf(petTypeString)
            LevelUpRoute(
                level = it.arguments?.getInt("level") ?: 1,
                petType = petType,
                onButtonClick = navController::popBackStack
            )
        }
        composable(DDanDDanRoute.NET_PET.route + "?petType={petType}") {
            val petTypeString = it.arguments?.getString("petType") ?: PetTypeEnum.CAT.name
            val petType = PetTypeEnum.valueOf(petTypeString)
            NewPetRoute(
                petType = petType,
                onButtonClick = navController::popBackStack
            )
        }
    }
}