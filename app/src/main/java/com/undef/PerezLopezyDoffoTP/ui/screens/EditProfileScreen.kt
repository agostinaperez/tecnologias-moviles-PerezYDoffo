package com.undef.PerezLopezyDoffoTP.ui.screens

import androidx.compose.material3.TextField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.undef.PerezLopezyDoffoTP.ui.components.Spacer
import com.undef.PerezLopezyDoffoTP.ui.navigation.Screen
import com.undef.PerezLopezyDoffoTP.ui.components.BackBar

@Preview(showBackground = true)
@Composable
fun PreviewEditProfileScreen(){
    EditProfileScreen(navController = rememberNavController())
}

@Composable
fun EditProfileScreen(navController: NavController){
    BackBar(navController){ paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ){
            EditProfile(modifier = Modifier, navController)
        }
    }
}

@Composable
fun EditProfile(modifier: Modifier, navController: NavController){
    var emailState by remember { mutableStateOf("user@gmail.com") }
    var usernameState by remember { mutableStateOf("username") }
    var passwordState by remember { mutableStateOf("pass123") }

    
    Text(text = "Edit Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                ProfileImage(modifier = modifier.padding(40.dp, 0.dp).size(200.dp))
            }
            EditParameter(modifier = modifier, emailState, "Email") { newEmail ->
                emailState = newEmail
            }
            EditParameter(modifier = modifier, usernameState, "Username") { newUsername ->
                usernameState = newUsername
            }
            EditParameter(modifier = modifier, passwordState, "Password") { newPassword ->
                passwordState = newPassword
            }
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = Modifier
                    .height(50.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Screen.Profile.route)
                        },
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(160.dp)
                    ) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = {
                            navController.navigate(Screen.Profile.route)
                        },
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(160.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}

@Composable
fun EditParameter(
    modifier: Modifier,
    input: String,
    label: String,
    onInputChange: (String) -> Unit
) {
    val passwordLabel = "Password"

    TextField(
        value = input,
        onValueChange = onInputChange,
        label = { Text(label) },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(15.dp)),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = false
        ),
        visualTransformation = if (label == passwordLabel ) PasswordVisualTransformation() else VisualTransformation.None,
    )
}


