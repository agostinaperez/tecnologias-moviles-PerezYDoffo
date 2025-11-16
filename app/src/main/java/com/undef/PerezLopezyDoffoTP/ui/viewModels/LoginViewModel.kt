package com.undef.PerezLopezyDoffoTP.ui.viewModels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.undef.PerezLopezyDoffoTP.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isValidPassword(password: String): Boolean = password.length > 6

    fun onLoginSelected() {
        val currentEmail = _email.value.orEmpty()
        val currentPassword = _password.value.orEmpty()
        if (!_loginEnable.value.orFalse()) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            runCatching {
                UserRepository.login(currentEmail, currentPassword)
            }.onSuccess {
                _loginSuccess.value = true
            }.onFailure { error ->
                _errorMessage.value = error.message ?: "Error al iniciar sesión"
            }
            _isLoading.value = false
        }
    }

    fun onLoginConsumed() {
        _loginSuccess.value = false
    }

    private fun Boolean?.orFalse(): Boolean = this ?: false
}
