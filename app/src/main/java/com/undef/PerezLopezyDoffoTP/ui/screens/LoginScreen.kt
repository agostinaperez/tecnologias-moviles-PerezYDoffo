package com.undef.PerezLopezyDoffoTP.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.undef.PerezLopezyDoffoTP.R
import com.undef.PerezLopezyDoffoTP.ui.viewModels.LoginViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import com.undef.PerezLopezyDoffoTP.ui.navigation.Screen
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(navController = rememberNavController())
}

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel = LoginViewModel()
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 15.dp)
    ) {
        Login(modifier = Modifier.fillMaxWidth(), viewModel, navController)
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel, navController: NavController) {
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            LaunchedEffect(Unit) {
                delay(1500)
                navController.navigate(Screen.Home.route){
                    popUpTo(Screen.Login.route){
                        inclusive = true
                    }
                }
                viewModel.resetLoading()
            }
        }
    } else {
        Column(modifier = modifier) {

            Spacer(modifier = Modifier.weight(1.3F))

            LoginImage(Modifier.align(Alignment.CenterHorizontally))

            Spacer(modifier = Modifier.weight(0.4F))

            FieldEmail(email) { viewModel.onLoginChanged(it, password) }

            FieldPassword(password) { viewModel.onLoginChanged(email, it) }

            TextRegister(modifier = Modifier.align(Alignment.Start), navController)

            ButtonLogin(loginEnable) {
                viewModel.onLoginSelected()
            }
            Spacer(modifier = Modifier.weight(2.6F))
        }
    }
}

@Composable
fun LoginImage(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "MANOS LOCALES",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun FieldEmail(email: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text("Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        singleLine = true
    )
}


@Composable
fun FieldPassword(password: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text("Password") },
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),

        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = false
        ),
        singleLine = true
    )
}

@Composable
fun TextRegister(modifier: Modifier, navController: NavController) {
    Text(
        text = "No tenés cuenta? Registrate",
        modifier = modifier
            .padding(top = 10.dp, bottom = 25.dp)
            .clickable {
                navController.navigate(Screen.SignUp.route)
            },)
}

@Composable
fun ButtonLogin(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = {
            onLoginSelected()
        },
        shape = RoundedCornerShape(5.dp),

        modifier = Modifier
            .fillMaxWidth()
            .size(50.dp),
        enabled = loginEnable
    ) {
        Text(text = "Login")
    }
}