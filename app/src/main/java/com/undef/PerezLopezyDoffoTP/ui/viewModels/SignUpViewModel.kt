package com.undef.PerezLopezyDoffoTP.ui.viewModels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.undef.PerezLopezyDoffoTP.repository.UserRepository
import kotlinx.coroutines.launch

class SignUpViewModel: ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _signUpSuccess = MutableLiveData<Boolean>()
    val signUpSuccess: LiveData<Boolean> = _signUpSuccess

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun onSignUpChanged(email: String, password: String, username: String) {
        _email.value = email
        _password.value = password
        _username.value = username
        _loginEnable.value = isValidEmail(email) && isValidPassword(password) && isValidUsername(username)
    }

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length > 6

    private fun isValidUsername(username: String): Boolean = username.length > 4

    fun onSignUpSelected() {
        val emailValue = _email.value.orEmpty()
        val passwordValue = _password.value.orEmpty()
        val usernameValue = _username.value.orEmpty()
        if (!_loginEnable.value.orFalse()) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            runCatching {
                UserRepository.register(
                    username = usernameValue,
                    email = emailValue,
                    password = passwordValue
                )
            }.onSuccess {
                _signUpSuccess.value = true
            }.onFailure { error ->
                _errorMessage.value = error.message ?: "No se pudo registrar"
            }
            _isLoading.value = false
        }
    }

    fun onSignUpConsumed() {
        _signUpSuccess.value = false
    }

    private fun Boolean?.orFalse(): Boolean = this ?: false
}
