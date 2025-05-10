package com.undef.PerezLopezyDoffoTP.ui.components
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val title: String
) {
    object Favorites : BottomNavItem("favs", Icons.Default.FavoriteBorder, "Favoritos")
    object Explore : BottomNavItem("home", Icons.Default.ShoppingCart, "Explorar")
    object Account : BottomNavItem("profile", Icons.Default.AccountCircle, "Perfil")
}