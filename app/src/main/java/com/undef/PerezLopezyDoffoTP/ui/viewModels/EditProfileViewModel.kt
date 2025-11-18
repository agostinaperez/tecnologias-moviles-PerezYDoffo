package com.undef.PerezLopezyDoffoTP.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.undef.PerezLopezyDoffoTP.repository.UserRepository
import com.undef.PerezLopezyDoffoTP.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EditProfileUiState(
    val username: String = "",
    val email: String = "",
    val currentPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val profileImage: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val hasChanges: Boolean = false
)

class EditProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()
    private var loadedUser: User? = null

    init {
        loadCurrentUser()
    }

    fun loadCurrentUser() {
        val userId = UserRepository.getCurrentUserId() ?: return
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, errorMessage = null) }
            runCatching {
                UserRepository.getUser(userId)
            }.onSuccess { user ->
                loadedUser = user
                updateState {
                    it.copy(
                        username = user.username,
                        email = user.email,
                        profileImage = user.profileImage,
                        isLoading = false
                    )
                }
            }.onFailure { error ->
                updateState {
                    it.copy(
                        errorMessage = error.message ?: "No se pudo cargar el perfil",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onUsernameChanged(value: String) {
        updateState { it.copy(username = value, errorMessage = null) }
    }

    fun onEmailChanged(value: String) {
        updateState { it.copy(email = value, errorMessage = null) }
    }

    fun onCurrentPasswordChanged(value: String) {
        updateState { it.copy(currentPassword = value, errorMessage = null) }
    }

    fun onNewPasswordChanged(value: String) {
        updateState { it.copy(newPassword = value, errorMessage = null) }
    }

    fun onConfirmPasswordChanged(value: String) {
        updateState { it.copy(confirmPassword = value, errorMessage = null) }
    }

    fun onProfileImageChanged(value: String) {
        val sanitized = value.ifBlank { null }
        updateState { it.copy(profileImage = sanitized, errorMessage = null) }
    }

    fun removeProfileImage() {
        updateState { it.copy(profileImage = null, errorMessage = null) }
    }

    fun reportImageError(message: String) {
        updateState { it.copy(errorMessage = message) }
    }

    fun saveChanges() {
        if (!_uiState.value.hasChanges || _uiState.value.isLoading) return
        val userId = UserRepository.getCurrentUserId() ?: return
        val currentState = _uiState.value
        val newPassword = currentState.newPassword.trim()
        val confirmPassword = currentState.confirmPassword.trim()
        if (newPassword.isBlank() && confirmPassword.isNotBlank()) {
            updateState { it.copy(errorMessage = "Ingresá la nueva contraseña para confirmar el cambio") }
            return
        }
        if (newPassword.isNotBlank()) {
            if (newPassword != confirmPassword) {
                updateState { it.copy(errorMessage = "Las contraseñas nuevas no coinciden") }
                return
            }
            val storedHash = loadedUser?.passwordHash
            val currentPassword = currentState.currentPassword
            if (storedHash.isNullOrBlank() || currentPassword.isBlank() ||
                !UserRepository.passwordMatches(currentPassword, storedHash)
            ) {
                updateState { it.copy(errorMessage = "La contraseña actual no es correcta") }
                return
            }
        }
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, errorMessage = null, isSuccess = false) }
            runCatching {
                UserRepository.updateUser(
                    userId = userId,
                    username = currentState.username.trim(),
                    email = currentState.email.trim(),
                    password = newPassword.ifBlank { null },
                    profileImage = currentState.profileImage
                )
            }.onSuccess { updatedUser ->
                loadedUser = updatedUser
                updateState { state ->
                    state.copy(
                        isLoading = false,
                        currentPassword = "",
                        newPassword = "",
                        confirmPassword = "",
                        profileImage = updatedUser.profileImage,
                        isSuccess = true
                    )
                }
            }.onFailure { error ->
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "No se pudo guardar el perfil"
                    )
                }
            }
        }
    }

    fun consumeSuccess() {
        updateState { it.copy(isSuccess = false) }
    }

    private fun updateState(transform: (EditProfileUiState) -> EditProfileUiState) {
        _uiState.update { current ->
            val newState = transform(current)
            newState.copy(hasChanges = computeHasChanges(newState))
        }
    }

    private fun computeHasChanges(state: EditProfileUiState): Boolean {
        val passwordChanged = state.currentPassword.isNotBlank() ||
            state.newPassword.isNotBlank() ||
            state.confirmPassword.isNotBlank()

        val user = loadedUser ?: return passwordChanged ||
            state.username.isNotBlank() ||
            state.email.isNotBlank() ||
            (state.profileImage?.isNotBlank() == true)

        val normalizedStateImage = state.profileImage?.takeIf { it.isNotBlank() }
        val normalizedUserImage = user.profileImage?.takeIf { it.isNotBlank() }

        val baseChanged = state.username != user.username ||
            state.email != user.email ||
            normalizedStateImage != normalizedUserImage

        return baseChanged || passwordChanged
    }
}
