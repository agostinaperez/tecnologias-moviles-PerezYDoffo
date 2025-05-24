package com.undef.PerezLopezyDoffoTP.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.undef.PerezLopezyDoffoTP.ui.screens.EditProfileScreen
import com.undef.PerezLopezyDoffoTP.ui.screens.EmprendimientoDetailScreen
import com.undef.PerezLopezyDoffoTP.ui.screens.FavsScreen
import  com.undef.PerezLopezyDoffoTP.ui.screens.LoginScreen
import  com.undef.PerezLopezyDoffoTP.ui.screens.ProfileScreen
import  com.undef.PerezLopezyDoffoTP.ui.screens.SplashScreen
import com.undef.PerezLopezyDoffoTP.ui.screens.HomeScreen
import com.undef.PerezLopezyDoffoTP.ui.screens.SearchScreen
import com.undef.PerezLopezyDoffoTP.ui.screens.SettingsScreen
import com.undef.PerezLopezyDoffoTP.ui.screens.SignUpScreen

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
        
        composable(route = Screen.SignUp.route){
            SignUpScreen(navController = navController)
        }

        composable(route = Screen.Profile.route){
            ProfileScreen(navController = navController)
        }

        composable(route = Screen.EditProfile.route){
            EditProfileScreen(navController = navController)
        }

        composable(route = Screen.Settings.route){
            SettingsScreen(navController = navController)
        }

        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screen.ProductoDetail.route) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getString("emprendimientoId")
            ProductoDetailScreen(navController = navController, emprendimientoId = emprendimientoId!!.toInt())
        }

        composable(route = Screen.Search.route) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")
            SearchScreen(navController = navController, title = title!!)
        }

        composable(route = Screen.Favs.route){
            FavsScreen(navController = navController)
        }

    }
}