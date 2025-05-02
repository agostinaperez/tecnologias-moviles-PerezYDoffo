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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.undef.PerezLopezyDoffoTP.R
import com.undef.PerezLopezyDoffoTP.ui.components.BottomNavBar

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Profile(modifier = Modifier, navController)
        }
    }
}

@Composable
fun Profile(modifier: Modifier, navController: NavController) {
    ProfileImage(modifier = modifier)
    Username(modifier = modifier)
    Spacer(modifier = Modifier.height(30.dp))
    Mail(modifier = modifier)
    Spacer(modifier = Modifier.height(30.dp))
    Box(
        modifier = Modifier
            .padding(20.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
    ){
        Menu(modifier = modifier)
    }
}

@Composable
fun Menu(modifier: Modifier){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xffebebeb), RoundedCornerShape(8.dp))
    ) {
        MenuItem(
            icon = Icons.Default.Edit,
            text = "Edit Profile",
            onClick = { /* Acción del menú */ }
        )
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        MenuItem(
            icon = Icons.Default.Settings,
            text = "Settings",
            onClick = { /* Acción del menú */ }
        )
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        MenuItem(
            icon = Icons.AutoMirrored.Filled.ExitToApp,
            text = "Logout",
            textColor = Color.Red,
            iconColor = Color.Red,
            onClick = { /* Acción del logout */ },
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
fun ProfileImage(modifier: Modifier) {
    Box(
        modifier = modifier
            .size(120.dp),
        //.border(2.dp, Color.Gray, CircleShape),
        contentAlignment = Alignment.BottomEnd,
    ){
        Image(painter = painterResource(id = R.drawable.blank_profile_pic),
            contentDescription = "Profile Image",
            modifier = modifier
                .size(120.dp)
                .clip(shape = CircleShape)
            //.border(2.dp, Color.Gray, CircleShape),
        )
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit Icon",
            modifier = Modifier
                .size(35.dp)
                .background(Color.Blue, CircleShape)
                .padding(4.dp)
                .clip(CircleShape),
            tint = Color.White
        )
    }
}

@Composable
fun Username(modifier: Modifier) {
    Text(
        text = "Username",
        modifier = modifier
            .padding(top = 10.dp),
        color = Color.Black,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun Mail(modifier: Modifier){
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFD6E4FF))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "tucorreo@gmail.com",
            color = Color.Black
        )
    }
}