package com.example.eventqr.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventqr.ui.screens.GuestsScreen
import com.example.eventqr.ui.screens.HomeScreen
import com.example.eventqr.ui.screens.ScanScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onOpenScanner = { navController.navigate(Routes.SCAN) },
                onOpenGuests = { navController.navigate(Routes.GUESTS) }
            )
        }

        composable(Routes.SCAN) {
            ScanScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.GUESTS) {
            GuestsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
