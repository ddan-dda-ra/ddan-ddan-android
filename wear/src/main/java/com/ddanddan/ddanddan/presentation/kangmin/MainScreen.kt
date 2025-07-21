package com.ddanddan.ddanddan.presentation.kangmin

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ddanddan.ddanddan.presentation.NotSupportedScreen
import com.ddanddan.ddanddan.presentation.kangmin.navigation.DDanWearRoute
import com.ddanddan.ddanddan.presentation.kangmin.permission.PermissionRoute
import com.ddanddan.ddanddan.presentation.kangmin.splash.SplashScreen
import com.ddanddan.ui.compose.DDanDDanColorPalette

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    navigateToCalorie: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        containerColor = DDanDDanColorPalette.current.color_background
    ) { padding ->
        NavHost(
            modifier = modifier.padding(padding),
            navController = navController,
            startDestination = DDanWearRoute.SPLASH.route
        ) {
            composable(route = DDanWearRoute.SPLASH.route) {
                SplashScreen(
                    onTimeout = {
                        navController.navigate(DDanWearRoute.PERMISSION.route) {
                            popUpTo(DDanWearRoute.SPLASH.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(route = DDanWearRoute.PERMISSION.route) {
                PermissionRoute(
                    navigateToCalorie = {
                        navigateToCalorie()
                        navController.navigate(DDanWearRoute.CALORIE.route) {
                            popUpTo(DDanWearRoute.PERMISSION.route) { inclusive = true }
                        }
                    },
                    navigateToNotSupported = {
                        navController.navigate(DDanWearRoute.NOT_SUPPORT_CALORIES.route)
                    }
                )
            }

            composable(route = DDanWearRoute.CALORIE.route) {
                CalorieRoute()
            }

            composable(route = DDanWearRoute.NOT_SUPPORT_CALORIES.route) {
                NotSupportedScreen()
            }
        }

    }
}