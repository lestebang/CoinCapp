package com.lesteban.coincapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lesteban.coincapp.ui.screen.AboutScreen
import com.lesteban.coincapp.ui.screen.CoinScreen
import com.lesteban.coincapp.ui.screen.FavoriteScreen
import com.lesteban.coincapp.ui.screen.MainScreen
import com.lesteban.coincapp.ui.screen.SearchScreen
import com.lesteban.coincapp.ui.screen.SettingsScreen
import com.lesteban.coincapp.ui.screen.SplashScreen
import com.lesteban.coincapp.utils.Constant
import com.lesteban.coincapp.viewmodel.SettingsViewmodel

@Composable
fun ScreenNavigation() {
    val navController = rememberNavController()
    val defaultScreen = EnumScreen.SPLASH_SCREEN.name

    NavHost(navController = navController, startDestination = defaultScreen) {

        composable(defaultScreen) {
            SplashScreen(navController)
        }

        composable(
            route = EnumScreen.MAIN_SCREEN.name
        ) {
            val coin = navController.currentBackStackEntry?.savedStateHandle?.get<String>(
                Constant.KEY_COUNTRY)
            MainScreen(navController, coin)
        }

        composable(route = EnumScreen.ABOUT_SCREEN.name) {
            AboutScreen(navController)
        }

        composable(route = EnumScreen.FAVORITE_SCREEN.name) {
            FavoriteScreen(navController)
        }

        composable(route = EnumScreen.COIN_SCREEN.name) {
            CoinScreen(navController)
        }

        composable(route = EnumScreen.SEARCH_SCREEN.name) {
            SearchScreen(navController)
        }

        composable(route = EnumScreen.SETTINGS_SCREEN.name) {
            val viewmodel = hiltViewModel<SettingsViewmodel>()
            SettingsScreen(navController, viewmodel)
        }
    }

}