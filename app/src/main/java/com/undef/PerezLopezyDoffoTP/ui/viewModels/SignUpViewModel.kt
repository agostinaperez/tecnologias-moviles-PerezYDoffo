package com.undef.PerezLopezyDoffoTP.ui.viewModels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

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
        _isLoading.value = true
    }

    suspend fun resetLoading() {
        delay(3000)
        _isLoading.value = false
    }

}