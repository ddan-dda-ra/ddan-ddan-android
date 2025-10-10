package com.ddanddan.ddanddan.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ddanddan.ddanddan.presentation.error.ErrorScreen
import com.ddanddan.ddanddan.presentation.friends.AddedFriendScreen
import com.ddanddan.ddanddan.presentation.friends.FriendsRoute
import com.ddanddan.ddanddan.presentation.home.GrantedNotRoute
import com.ddanddan.ddanddan.presentation.home.HomeRoute
import com.ddanddan.ddanddan.presentation.home.collect.PetCollectionRoute
import com.ddanddan.ddanddan.presentation.home.reward.ToyRewardScreen
import com.ddanddan.ddanddan.presentation.home.reward.level.LevelUpRoute
import com.ddanddan.ddanddan.presentation.home.reward.pet.NewPetRoute
import com.ddanddan.ddanddan.presentation.navigation.DDanDDanBottomBar
import com.ddanddan.ddanddan.presentation.navigation.DDanDDanRoute
import com.ddanddan.ddanddan.presentation.onboarding.OnboardingRoute
import com.ddanddan.ddanddan.presentation.rank.RankRoute
import com.ddanddan.ddanddan.presentation.setting.SettingRoute
import com.ddanddan.ddanddan.presentation.setting.WebViewScreen
import com.ddanddan.ddanddan.presentation.setting.nickname.EditNickNameRoute
import com.ddanddan.ddanddan.presentation.setting.onAgreeScreen
import com.ddanddan.ddanddan.presentation.setting.signout.SignOutFirstRoute
import com.ddanddan.ddanddan.presentation.setting.signout.SignOutSecondRoute
import com.ddanddan.ddanddan.presentation.setting.target.EditTargetRoute
import com.ddanddan.ddanddan.presentation.setting.viewModel.SettingViewModel
import com.ddanddan.ddanddan.presentation.signin.SignInRoute
import com.ddanddan.ddanddan.presentation.signup.SignUpViewModel
import com.ddanddan.ddanddan.presentation.signup.egg.SetEggRoute
import com.ddanddan.ddanddan.presentation.signup.finish.onSignUpDoneScreen
import com.ddanddan.ddanddan.presentation.signup.name.SetNameRoute
import com.ddanddan.ddanddan.presentation.signup.target.SetTargetRoute
import com.ddanddan.ddanddan.presentation.signup.terms.onTermsScreen
import com.ddanddan.ddanddan.presentation.splash.SplashRoute
import com.ddanddan.domain.enums.PetTypeEnum
import com.ddanddan.ui.ext.sharedViewModel

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarScreens = setOf(
        DDanDDanRoute.HOME.route,
        DDanDDanRoute.RANKING.route,
        DDanDDanRoute.FRIENDS.route,
        DDanDDanRoute.SETTING.route
    )

    Scaffold(
        bottomBar = {
            if (currentRoute?.substringBefore("?") in bottomBarScreens) {
                DDanDDanBottomBar(
                    currentRoute = currentRoute,
                    onItemClick = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = DDanDDanRoute.SPLASH.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
        ) {
            composable(
                route = DDanDDanRoute.HOME.route +"?isNewPet={isNewPet}",
                arguments = listOf(navArgument("isNewPet") {
                    type = NavType.BoolType; defaultValue = false
                })
            ) { navBackStackEntry ->
                val needRefresh by navBackStackEntry.savedStateHandle
                    .getStateFlow("needRefresh", false)
                    .collectAsState()

                HomeRoute(
                    needRefresh = needRefresh,
                    onNavigateLevelUp = { level, petType ->
                        navController.navigate(DDanDDanRoute.LEVEL_UP.route + "?level=${level}&petType=${petType}")
                    },
                    onNavigateError = { errorCode ->
                        navController.navigate(DDanDDanRoute.ERROR.route + "?errorCode=${errorCode}")
                    }
                )
            }
            composable(route = DDanDDanRoute.PET_COLLECTION.route) {
                PetCollectionRoute(
                    navigatePopUp = navController::popBackStack,
                    onConfirmClick = {
                        navController.navigate(DDanDDanRoute.HOME.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
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
                    },
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
                        navController.navigate(DDanDDanRoute.SIGN_IN.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    },
                    onPetCollectionClick = {
                        navController.navigate(DDanDDanRoute.PET_COLLECTION.route)
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
                    navigateOnBoarding = {
                        navController.navigate(DDanDDanRoute.ONBOARDING.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    }
                )
            }

            composable(DDanDDanRoute.TOY_REWARD.route) {
                ToyRewardScreen()
            }
            composable(
                route = DDanDDanRoute.LEVEL_UP.route + "?level={level}" + "&petType={petType}",
                arguments = listOf(
                    navArgument("level") {
                        type = NavType.IntType; defaultValue = 0
                    },
                    navArgument("petType") {
                        type = NavType.StringType; defaultValue = PetTypeEnum.CAT.name
                    })
            ) {
                val petTypeString = it.arguments?.getString("petType") ?: PetTypeEnum.CAT.name
                val petType = PetTypeEnum.valueOf(petTypeString)
                LevelUpRoute(
                    level = it.arguments?.getInt("level") ?: 1,
                    petType = petType,
                    onButtonClick = navController::popBackStack,
                    navigateToNetPet = {
                        navController.navigate(DDanDDanRoute.NET_PET.route)
                    }
                )
            }
            composable(DDanDDanRoute.NET_PET.route) {
                NewPetRoute(
                    onButtonClick = {
                        navController.navigate(DDanDDanRoute.HOME.route + "?isNewPet=true") {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    }
                )
            }
            composable(
                route = DDanDDanRoute.ERROR.route + "?errorCode={errorCode}",
                arguments = listOf(navArgument("errorCode") {
                    type = NavType.IntType; defaultValue = 0
                })
            ) {
                val errorCode = it.arguments?.getInt("errorCode") ?: 0
                ErrorScreen(
                    isNotPage = errorCode == 0,
                    errorCode = if (errorCode == 0) null else errorCode,
                    onMoveHomeClicked = navController::popBackStack
                )
            }

            composable(route = DDanDDanRoute.SIGN_IN.route) {
                SignInRoute(
                    onNavigateSignUp = {
                        navController.navigate(DDanDDanRoute.SIGN_UP_TERM.route)
                    },
                    onNavigateHome = {
                        navController.navigate(DDanDDanRoute.HOME.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    }
                )
            }

            composable(route = DDanDDanRoute.ONBOARDING.route) {
                OnboardingRoute(
                    onNavigateSignIn = {
                        navController.navigate(DDanDDanRoute.SIGN_IN.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    }
                )
            }

            composable(route = DDanDDanRoute.SPLASH.route) {
                SplashRoute(
                    onNavigateHome = {
                        navController.navigate(DDanDDanRoute.HOME.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    },
                    onNavigateOnboarding = {
                        navController.navigate(DDanDDanRoute.ONBOARDING.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    },
                    onNavigateSignIn = {
                        navController.navigate(DDanDDanRoute.SIGN_IN.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    },
                    onNavigateGrantNotPermission = {
                        navController.navigate(DDanDDanRoute.GRANT_NOT_PERMISSION.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    }
                )
            }

            composable(DDanDDanRoute.SIGN_UP_TERM.route) {
                onTermsScreen(
                    navController = navController,
                    onAgreeTerms = {
                        navController.navigate(DDanDDanRoute.SIGN_UP_NICKNAME.route)
                    }
                )
            }

            composable(DDanDDanRoute.SIGN_UP_NICKNAME.route) { navBackStackEntry ->
                val viewModel = navBackStackEntry.sharedViewModel<SignUpViewModel>(
                    navController = navController,
                    navGraphRoute = DDanDDanRoute.SIGN_UP_TERM.route
                )
                SetNameRoute(
                    signUpViewModel = viewModel,
                    onNavigateTargetCalories = {
                        navController.navigate(DDanDDanRoute.SIGN_UP_TARGET.route)
                    },
                    onNavigateTerms = {
                        navController.navigate(DDanDDanRoute.SIGN_UP_TERM.route)
                    }
                )
            }

            composable(DDanDDanRoute.SIGN_UP_TARGET.route) { navBackStackEntry ->
                val viewModel = navBackStackEntry.sharedViewModel<SignUpViewModel>(
                    navController = navController,
                    navGraphRoute = DDanDDanRoute.SIGN_UP_TERM.route
                )
                SetTargetRoute(
                    signUpViewModel = viewModel,
                    onNavigatePetType = {
                        navController.navigate(DDanDDanRoute.SIGN_UP_EGG.route)
                    },
                    onNavigateSetName = {
                        navController.navigate(DDanDDanRoute.SIGN_UP_NICKNAME.route)
                    }
                )
            }

            composable(DDanDDanRoute.SIGN_UP_EGG.route) { navBackStackEntry ->
                val viewModel = navBackStackEntry.sharedViewModel<SignUpViewModel>(
                    navController = navController,
                    navGraphRoute = DDanDDanRoute.SIGN_UP_TERM.route
                )
                SetEggRoute(
                    signUpViewModel = viewModel,
                    onNavigateFinish = {
                        navController.navigate(DDanDDanRoute.SIGN_UP_DONE.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    },
                    onNavigateGoal = {
                        navController.navigate(DDanDDanRoute.SIGN_UP_TARGET.route)
                    }
                )
            }

            composable(DDanDDanRoute.SIGN_UP_DONE.route) {
                onSignUpDoneScreen(
                    onNavigateHome = {
                        navController.navigate(DDanDDanRoute.HOME.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    }
                )
            }

            composable(DDanDDanRoute.RANKING.route) {
                RankRoute(navigatePopUp = navController::popBackStack)
            }

            composable(DDanDDanRoute.GRANT_NOT_PERMISSION.route) {
                GrantedNotRoute(
                    onNavigateHome = {
                        navController.navigate(DDanDDanRoute.HOME.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    },
                    onNavigateOnboarding = {
                        navController.navigate(DDanDDanRoute.ONBOARDING.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    },
                    onNavigateSignIn = {
                        navController.navigate(DDanDDanRoute.SIGN_IN.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    },
                )
            }

            composable(DDanDDanRoute.FRIENDS.route) {
                FriendsRoute()
            }

            composable(DDanDDanRoute.ADDED_FRIEND.route) {
                AddedFriendScreen(
                    onNavigateFriends = {
                        navController.navigate(DDanDDanRoute.FRIENDS.route) {
                            popUpTo(navController.graph.id) { inclusive = true}
                        }
                    }
                )
            }
        }
    }
}