package com.undef.PerezLopezyDoffoTP.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.undef.PerezLopezyDoffoTP.R
import com.undef.PerezLopezyDoffoTP.ui.viewModels.SignUpViewModel
import kotlinx.coroutines.delay
import com.undef.PerezLopezyDoffoTP.ui.navigation.Screen
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.undef.PerezLopezyDoffoTP.ui.components.BackBar

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen(navController = rememberNavController())
}

@Composable
fun SignUpScreen(navController: NavController){
    val viewModel = SignUpViewModel()
    BackBar(navController){ paddingValues ->
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(paddingValues)
                .padding(16.dp, 0.dp)
        ) {
            SignUp(modifier = Modifier.fillMaxWidth(), viewModel, navController)
        }
    }
}
@Composable
fun SignUp(modifier: Modifier, viewModel: SignUpViewModel, navController: NavController){
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val username: String by viewModel.username.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            LaunchedEffect(Unit) {
                delay(1500)
                navController.navigate(Screen.Home.route){
                    popUpTo(Screen.SignUp.route){
                        inclusive = true
                    }
                }
                viewModel.resetLoading()
            }
        }
    } else {
        Column(modifier = modifier) {

            //Spacer(modifier = Modifier.weight(1.3F))

            SignUpImage(Modifier.align(Alignment.CenterHorizontally))

            Spacer(modifier = Modifier.weight(0.4F))

            FieldUsername(username) { viewModel.onSignUpChanged(email, password, it) }

            FieldEmail(email) { viewModel.onSignUpChanged(it, password, username) }

            FieldPassword(password) { viewModel.onSignUpChanged(email, it, username) }

            ButtonSignUp(loginEnable) {
                viewModel.onSignUpSelected()
            }
            Spacer(modifier = Modifier.weight(2.6F))
        }
    }
}

@Composable
fun SignUpImage(modifier: Modifier){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "MANOS LOCALES",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun FieldUsername(username: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = username,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text("Username") },
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = false
        ),
        singleLine = true
    )
}

@Composable
fun ButtonSignUp(loginEnable: Boolean, onSignUpSelected: () -> Unit){
    Button(
        onClick = {
            onSignUpSelected()
        },
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .size(50.dp),
        enabled = loginEnable
    ) {
        Text(text = "Sign Up")
    }
}