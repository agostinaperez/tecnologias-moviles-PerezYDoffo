package com.undef.PerezLopezyDoffoTP.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")

    object Login : Screen("login")

    object Profile : Screen("profile")
}