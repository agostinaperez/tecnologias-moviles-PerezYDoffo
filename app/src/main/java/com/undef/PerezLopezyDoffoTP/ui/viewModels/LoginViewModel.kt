package com.undef.PerezLopezyDoffoTP.ui.viewModels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.undef.PerezLopezyDoffoTP.repository.BiometricAuthManager
import com.undef.PerezLopezyDoffoTP.repository.EmprendimientoRepository
import com.undef.PerezLopezyDoffoTP.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _rememberMe = MutableLiveData<Boolean>()
    val rememberMe: LiveData<Boolean> = _rememberMe

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _biometricAvailable = MutableLiveData<Boolean>()
    val biometricAvailable: LiveData<Boolean> = _biometricAvailable

    init {
        val remembered = UserRepository.getRememberedCredentials()
        if (remembered != null) {
            _email.value = remembered.email
            _password.value = remembered.password
            _rememberMe.value = true
            _loginEnable.value = isValidEmail(remembered.email) && remembered.password.isNotBlank()
        } else {
            _rememberMe.value = false
        }
        refreshBiometricAvailability()
    }

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && password.isNotBlank()
    }

    fun onRememberMeChanged(checked: Boolean) {
        _rememberMe.value = checked
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun onLoginSelected() {
        val currentEmail = _email.value.orEmpty()
        val currentPassword = _password.value.orEmpty()
        val remember = _rememberMe.value.orFalse()
        if (!_loginEnable.value.orFalse()) return
        loginInternal(currentEmail, currentPassword, remember)
    }

    fun loginWithBiometrics() {
        val credentials = BiometricAuthManager.getCredentials()
        if (!BiometricAuthManager.isEnabled() || credentials == null) {
            _errorMessage.value = "Configurá el login biométrico en Ajustes"
            _biometricAvailable.value = false
            return
        }
        loginInternal(credentials.email, credentials.password, remember = true)
    }

    fun onLoginConsumed() {
        _loginSuccess.value = false
    }

    fun refreshBiometricAvailability() {
        _biometricAvailable.value =
            BiometricAuthManager.isEnabled() && BiometricAuthManager.hasCredentials()
    }

    fun onBiometricError(message: String?) {
        if (!message.isNullOrBlank()) {
            _errorMessage.value = message
        }
    }

    private fun loginInternal(email: String, password: String, remember: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            runCatching {
                val user = UserRepository.login(email, password, remember)
                EmprendimientoRepository.syncFavoritesForUser(user.id)
            }.onSuccess {
                _loginSuccess.value = true
                refreshBiometricAvailability()
            }.onFailure { error ->
                _errorMessage.value = error.message ?: "Error al iniciar sesión"
            }
            _isLoading.value = false
        }
    }

    private fun Boolean?.orFalse(): Boolean = this ?: false
}
