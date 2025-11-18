package com.undef.PerezLopezyDoffoTP.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.undef.PerezLopezyDoffoTP.R
import com.undef.PerezLopezyDoffoTP.ui.components.MainScaffold
import com.undef.PerezLopezyDoffoTP.ui.navigation.Screen
import com.undef.PerezLopezyDoffoTP.ui.viewModels.ProfileViewModel
import coil.compose.AsyncImage
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ProfileScreen(navController: NavController) {
    val viewModel: ProfileViewModel = viewModel()
    val user by viewModel.user.observeAsState()
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val errorMessage: String? by viewModel.errorMessage.observeAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.loadUser()
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadUser()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    MainScaffold(navController = navController) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(15.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator()
                }
                !errorMessage.isNullOrEmpty() -> {
                    Text(
                        text = errorMessage ?: "",
                        color = Color.Red
                    )
                }
                user == null -> {
                    ProfileEmptyState {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                }
                else -> {
                    Profile(
                        modifier = Modifier,
                        username = user?.username.orEmpty(),
                        email = user?.email.orEmpty(),
                        profileImage = user?.profileImage,
                        onEditProfile = { navController.navigate(Screen.EditProfile.route) },
                        onOpenSettings = { navController.navigate(Screen.Settings.route) },
                        onLogout = {
                            viewModel.logout()
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Profile(
    modifier: Modifier,
    username: String,
    email: String,
    profileImage: String?,
    onEditProfile: () -> Unit,
    onOpenSettings: () -> Unit,
    onLogout: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        ProfileImage(
            modifier = Modifier.size(140.dp),
            imageUrl = profileImage,
            onChangePhoto = null
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    Username(modifier = modifier, username = username)
    Spacer(modifier = Modifier.height(30.dp))
    Mail(modifier = modifier, email = email)
    Spacer(modifier = Modifier.height(30.dp))
    Box(
        modifier = Modifier
            .padding(20.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
    ){
        Menu(
            modifier = modifier,
            onEditProfile = onEditProfile,
            onOpenSettings = onOpenSettings,
            onLogout = onLogout
        )
    }
}

@Composable
fun Menu(
    modifier: Modifier,
    onEditProfile: () -> Unit,
    onOpenSettings: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xffebebeb), RoundedCornerShape(8.dp))
    ) {
        MenuItem(
            icon = Icons.Default.Edit,
            text = "Edit Profile",
            onClick = onEditProfile
        )
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        MenuItem(
            icon = Icons.Default.Settings,
            text = "Settings",
            onClick = onOpenSettings
        )
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        MenuItem(
            icon = Icons.AutoMirrored.Filled.ExitToApp,
            text = "Logout",
            textColor = Color.Red,
            iconColor = Color.Red,
            onClick = onLogout,
            showArrow = false
        )
    }
}

@Composable
fun MenuItem(
    icon: ImageVector,
    text: String,
    textColor: Color = Color.Black,
    iconColor: Color = Color.Blue,
    onClick: () -> Unit,
    showArrow: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (showArrow){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    onChangePhoto: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.White)
            .clickable(enabled = onChangePhoto != null) {
                onChangePhoto?.invoke()
            },
        contentAlignment = Alignment.BottomEnd
    ) {
        if (imageUrl.isNullOrBlank()) {
            Image(
                painter = painterResource(id = R.drawable.blank_profile_pic),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        if (onChangePhoto != null) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar foto",
                modifier = Modifier
                    .size(36.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .padding(6.dp)
                    .clip(CircleShape),
                tint = Color.White
            )
        }
    }
}

@Composable
fun Username(modifier: Modifier, username: String) {
    Text(
        text = "Hola, $username!",
        modifier = modifier
            .padding(top = 10.dp),
        color = Color.Black,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Mail(modifier: Modifier, email: String){
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFD6E4FF))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = email,
            color = Color.Black
        )
    }
}

@Composable
fun ProfileEmptyState(onNavigateToLogin: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Necesitás iniciar sesión para ver tu perfil.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Ir a Login",
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF1E88E5))
                .clickable { onNavigateToLogin() }
                .padding(horizontal = 24.dp, vertical = 10.dp),
            color = Color.White
        )
    }
}
