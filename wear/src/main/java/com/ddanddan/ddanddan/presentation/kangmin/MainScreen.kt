package com.ddanddan.ddanddan.presentation.kangmin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ddanddan.ddanddan.presentation.kangmin.navigation.DDanWearRoute
import com.ddanddan.ui.compose.DDanDDanColorPalette

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        modifier = modifier,
        containerColor = DDanDDanColorPalette.current.color_background
    ) { padding ->
        NavHost(
            modifier = modifier.padding(padding),
            navController = navController,
            startDestination = DDanWearRoute.PERMISSION.route
        ) {
            composable(route = DDanWearRoute.PERMISSION.route) {
                PermissionRoute()
            }
        }

    }
}