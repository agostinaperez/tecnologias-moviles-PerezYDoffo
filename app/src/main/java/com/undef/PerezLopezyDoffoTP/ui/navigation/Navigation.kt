package com.undef.PerezLopezyDoffoTP.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import  com.undef.PerezLopezyDoffoTP.ui.screens.LoginScreen
import  com.undef.PerezLopezyDoffoTP.ui.screens.ProfileScreen
import  com.undef.PerezLopezyDoffoTP.ui.screens.SplashScreen
import com.undef.PerezLopezyDoffoTP.ui.screens.HomeScreen

@Composable
fun SetupNavigation (){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route){
        composable(route = Screen.Splash.route){
            SplashScreen(navController = navController)
        }

        composable(route = Screen.Login.route){
            LoginScreen(navController = navController)
        }

        composable(route = Screen.Profile.route){
            ProfileScreen(navController = navController)
        }

        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
    }
}