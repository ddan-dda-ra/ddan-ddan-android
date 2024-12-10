package com.ddanddan.ddanddan.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ddanddan.ddanddan.presentation.error.ErrorScreen
import com.ddanddan.ddanddan.presentation.home.HomeRoute
import com.ddanddan.ddanddan.presentation.home.collect.PetCollectionRoute
import com.ddanddan.ddanddan.presentation.home.reward.ToyRewardScreen
import com.ddanddan.ddanddan.presentation.home.reward.level.LevelUpRoute
import com.ddanddan.ddanddan.presentation.home.reward.pet.NewPetRoute
import com.ddanddan.ddanddan.presentation.navigation.DDanDDanRoute
import com.ddanddan.ddanddan.presentation.setting.SettingRoute
import com.ddanddan.ddanddan.presentation.setting.WebViewScreen
import com.ddanddan.ddanddan.presentation.setting.nickname.EditNickNameRoute
import com.ddanddan.ddanddan.presentation.setting.onAgreeScreen
import com.ddanddan.ddanddan.presentation.setting.signout.SignOutFirstRoute
import com.ddanddan.ddanddan.presentation.setting.signout.SignOutSecondRoute
import com.ddanddan.ddanddan.presentation.setting.target.EditTargetRoute
import com.ddanddan.ddanddan.presentation.setting.viewModel.SettingViewModel
import com.ddanddan.domain.enums.PetTypeEnum
import com.ddanddan.ui.ext.sharedViewModel

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    onNavigateOnBoarding: () -> Unit = {},
    onNavigateLogin: () -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = DDanDDanRoute.HOME.route
    ) {
        composable(DDanDDanRoute.HOME.route) { navBackStackEntry ->
            val needRefresh by navBackStackEntry.savedStateHandle
                .getStateFlow("needRefresh", false)
                .collectAsState()

            HomeRoute(
                needRefresh = needRefresh,
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
                },
                onNavigateError = { errorCode ->
                    navController.navigate(DDanDDanRoute.ERROR.route + "?errorCode=${errorCode}")
                }
            )
        }
        composable(
            route = DDanDDanRoute.PET_COLLECTION.route + "?petId={petId}",
            arguments = listOf(navArgument("petId") { type = NavType.StringType; defaultValue = "" })
        ) {
             PetCollectionRoute(
                navigatePopUp = navController::popBackStack,
                onConfirmClick = {
                    navController.navigate(DDanDDanRoute.HOME.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = DDanDDanRoute.SETTING.route
        ) { navBackStackEntry ->
            val viewModel = navBackStackEntry.sharedViewModel<SettingViewModel>(
                navController = navController,
                navGraphRoute = DDanDDanRoute.HOME.route
            )
            SettingRoute(
                viewModel = viewModel,
                navigatePopUp = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("needRefresh", it)
                    navController.popBackStack()
                }
                ,
                onNickNameClick = {
                    navController.navigate(DDanDDanRoute.EDIT_NICKNAME.route)
                },
                onCaloriesClick = {
                    navController.navigate(DDanDDanRoute.EDIT_TARGET.route)
                },
                onAgreeClick = {
                    navController.navigate(DDanDDanRoute.ON_AGREE.route)
                },
                onSignOutClick = {
                    navController.navigate(DDanDDanRoute.SIGN_OUT_FIRST.route)
                },
                navigateLogin = {
                    onNavigateLogin()
                }
            )
        }

        composable(DDanDDanRoute.ON_AGREE.route) {
            onAgreeScreen(
                navController = navController,
                onTopBarBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = DDanDDanRoute.WEBVIEW.route + "?url={url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val url = navBackStackEntry.arguments?.getString("url") ?: "https://www.naver.com"
            WebViewScreen(url = url)
        }

        composable(DDanDDanRoute.EDIT_NICKNAME.route) { navBackStackEntry ->
            val viewModel = navBackStackEntry.sharedViewModel<SettingViewModel>(
                navController = navController,
                navGraphRoute = DDanDDanRoute.HOME.route
            )
            EditNickNameRoute(
                viewModel = viewModel,
                navigatePopUp = navController::popBackStack

            )
        }

        composable(DDanDDanRoute.EDIT_TARGET.route) { navBackStackEntry ->
            val viewModel = navBackStackEntry.sharedViewModel<SettingViewModel>(
                navController = navController,
                navGraphRoute = DDanDDanRoute.HOME.route
            )
            EditTargetRoute(
                viewModel = viewModel,
                navigatePopUp = navController::popBackStack
            )
        }

        composable(DDanDDanRoute.SIGN_OUT_FIRST.route) { navBackStackEntry ->
            val viewModel = navBackStackEntry.sharedViewModel<SettingViewModel>(
                navController = navController,
                navGraphRoute = DDanDDanRoute.HOME.route
            )
            SignOutFirstRoute(
                viewModel = viewModel,
                navigatePopUp = navController::popBackStack,
                navigateSignOutSecond = {
                    navController.navigate(DDanDDanRoute.SIGN_OUT_SECOND.route)
                }
            )
        }

        composable(DDanDDanRoute.SIGN_OUT_SECOND.route) { navBackStackEntry ->
            val viewModel = navBackStackEntry.sharedViewModel<SettingViewModel>(
                navController = navController,
                navGraphRoute = DDanDDanRoute.HOME.route
            )
            SignOutSecondRoute(
                viewModel = viewModel,
                navigatePopUp = navController::popBackStack,
                navigateOnBoarding = onNavigateOnBoarding
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
        composable(
            route = DDanDDanRoute.ERROR.route + "?errorCode={errorCode}",
            arguments = listOf(navArgument("errorCode") { type = NavType.IntType; defaultValue = 0 })) {
            val errorCode = it.arguments?.getInt("errorCode") ?: 0
            ErrorScreen(
                isNotPage = errorCode == 0,
                errorCode = if (errorCode == 0) null else errorCode,
                onMoveHomeClicked = navController::popBackStack
            )
        }
    }
}