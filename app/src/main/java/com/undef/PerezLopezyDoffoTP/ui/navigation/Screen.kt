package com.undef.PerezLopezyDoffoTP.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")

    object Login : Screen("login")

    object SignUp : Screen("signup")

    object Profile : Screen("profile")

    object Home : Screen("home")

    object EmprendimientoDetail : Screen("emprendimientoDetail/{emprendimientoId}")

    object Search : Screen("search?title={title}")

    object EditProfile: Screen("editprofile")

    object Settings: Screen("settings")

    object Favs: Screen("favs")
}