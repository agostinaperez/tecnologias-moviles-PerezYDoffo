package com.undef.PerezLopezyDoffoTP.ui.screens

import android.content.Context
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.undef.PerezLopezyDoffoTP.ui.components.BackBar
import com.undef.PerezLopezyDoffoTP.ui.components.Spacer
import com.undef.PerezLopezyDoffoTP.ui.navigation.Screen
import com.undef.PerezLopezyDoffoTP.ui.viewModels.EditProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Preview(showBackground = true)
@Composable
fun PreviewEditProfileScreen(){
    EditProfileScreen(navController = rememberNavController(), viewModel = EditProfileViewModel())
}

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileViewModel = viewModel()
) {
    BackBar(navController){ paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ){
            EditProfile(modifier = Modifier, navController, viewModel)
        }
    }
}

@Composable
fun EditProfile(modifier: Modifier, navController: NavController, viewModel: EditProfileViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val saveEnabled = uiState.hasChanges && !uiState.isLoading
    val scrollState = rememberScrollState()
    val pickPhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            coroutineScope.launch {
                val dataUri = context.encodeImageToDataUri(uri)
                if (dataUri != null) {
                    viewModel.onProfileImageChanged(dataUri)
                } else {
                    viewModel.reportImageError("No pudimos procesar la imagen seleccionada")
                }
            }
        }
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.navigate(Screen.Profile.route) {
                popUpTo(Screen.Profile.route) { inclusive = true }
            }
            viewModel.consumeSuccess()
        }
    }

    val openImagePicker = {
        pickPhotoLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "Editar perfil",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                ProfileImage(
                    modifier = modifier
                        .padding(top = 8.dp)
                        .size(180.dp),
                    imageUrl = uiState.profileImage,
                    onChangePhoto = openImagePicker
                )
            }
            if (!uiState.profileImage.isNullOrBlank()) {
                TextButton(
                    onClick = viewModel::removeProfileImage,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Quitar foto")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Datos personales",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            EditParameter(
                modifier = modifier,
                input = uiState.username,
                label = "Username",
                onInputChange = viewModel::onUsernameChanged,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrectEnabled = true
                )
            )
            EditParameter(
                modifier = modifier,
                input = uiState.email,
                label = "Email",
                onInputChange = viewModel::onEmailChanged,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Email
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Seguridad",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Completá estos campos solo si querés actualizar tu contraseña.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            EditParameter(
                modifier = modifier,
                input = uiState.currentPassword,
                label = "Contraseña actual",
                onInputChange = viewModel::onCurrentPasswordChanged,
                isPassword = true
            )
            EditParameter(
                modifier = modifier,
                input = uiState.newPassword,
                label = "Nueva contraseña",
                onInputChange = viewModel::onNewPasswordChanged,
                isPassword = true
            )
            EditParameter(
                modifier = modifier,
                input = uiState.confirmPassword,
                label = "Confirmar nueva contraseña",
                onInputChange = viewModel::onConfirmPasswordChanged,
                isPassword = true
            )
            if (!uiState.errorMessage.isNullOrEmpty()) {
                Text(
                    text = uiState.errorMessage ?: "",
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            Button(
                onClick = viewModel::saveChanges,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(8.dp),
                enabled = saveEnabled
            ) {
                Text(text = "Guardar cambios")
            }
            TextButton(
                onClick = {
                    navController.navigateUp()
                },
                enabled = !uiState.isLoading,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Cancelar")
            }
        }
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun EditParameter(
    modifier: Modifier,
    input: String,
    label: String,
    onInputChange: (String) -> Unit,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.None,
        autoCorrectEnabled = false
    )
) {
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
        keyboardOptions = keyboardOptions,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
    )
}

private suspend fun Context.encodeImageToDataUri(uri: Uri): String? = withContext(Dispatchers.IO) {
    runCatching {
        val mimeType = contentResolver.getType(uri) ?: "image/jpeg"
        val bytes = contentResolver.openInputStream(uri)?.use { it.readBytes() } ?: return@runCatching null
        val base64 = Base64.encodeToString(bytes, Base64.NO_WRAP)
        "data:$mimeType;base64,$base64"
    }.getOrNull()
}
