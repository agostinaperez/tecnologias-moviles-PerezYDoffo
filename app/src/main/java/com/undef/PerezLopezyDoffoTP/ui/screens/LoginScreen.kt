package com.undef.PerezLopezyDoffoTP.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
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
import com.undef.PerezLopezyDoffoTP.ui.navigation.Screen
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(navController = rememberNavController(), loginViewModel = LoginViewModel())
}

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 15.dp)
    ) {
        Login(modifier = Modifier.fillMaxWidth(), loginViewModel, navController)
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel, navController: NavController) {
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val rememberMe: Boolean by viewModel.rememberMe.observeAsState(initial = false)
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val loginSuccess: Boolean by viewModel.loginSuccess.observeAsState(initial = false)
    val errorMessage: String? by viewModel.errorMessage.observeAsState()
    val biometricAvailable: Boolean by viewModel.biometricAvailable.observeAsState(initial = false)

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) {
                    inclusive = true
                }
            }
            viewModel.onLoginConsumed()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.refreshBiometricAvailability()
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else {
        Column(modifier = modifier) {

            Spacer(modifier = Modifier.weight(1.3F))

            LoginImage(Modifier.align(Alignment.CenterHorizontally))

            Spacer(modifier = Modifier.weight(0.4F))

            FieldEmail(email) { viewModel.onLoginChanged(it, password) }

            FieldPassword(password) { viewModel.onLoginChanged(email, it) }

            RememberMeOption(
                checked = rememberMe,
                onCheckedChange = { viewModel.onRememberMeChanged(it) }
            )

            TextRegister(modifier = Modifier.align(Alignment.Start), navController)

            if (!errorMessage.isNullOrEmpty()) {
                Text(
                    text = errorMessage ?: "",
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            ButtonLogin(loginEnable) {
                viewModel.onLoginSelected()
            }
            if (biometricAvailable) {
                Spacer(modifier = Modifier.height(12.dp))
                BiometricLoginButton(viewModel = viewModel)
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
fun RememberMeOption(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = "Recordarme",
            modifier = Modifier
                .clickable { onCheckedChange(!checked) }
                .padding(start = 8.dp)
        )
    }
}

@Composable
fun BiometricLoginButton(viewModel: LoginViewModel) {
    val context = LocalContext.current
    val activity = context as? FragmentActivity ?: return
    val executor = remember(context) { ContextCompat.getMainExecutor(context) }
    val promptInfo = remember {
        val builder = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Inicio de sesión biométrico")
            .setSubtitle("Usá tu huella o rostro para continuar")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            builder.setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
        } else {
            @Suppress("DEPRECATION")
            builder.setDeviceCredentialAllowed(true)
        }
        builder.build()
    }
    Button(
        onClick = {
            val prompt = BiometricPrompt(
                activity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        viewModel.loginWithBiometrics()
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        if (errorCode != BiometricPrompt.ERROR_NEGATIVE_BUTTON &&
                            errorCode != BiometricPrompt.ERROR_USER_CANCELED &&
                            errorCode != BiometricPrompt.ERROR_CANCELED
                        ) {
                            viewModel.onBiometricError(errString.toString())
                        }
                    }

                    override fun onAuthenticationFailed() {
                        viewModel.onBiometricError("No pudimos validar tu huella, intentalo nuevamente")
                    }
                }
            )
            prompt.authenticate(promptInfo)
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = "Ingresar con biometría")
    }
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
