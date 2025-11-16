package com.undef.PerezLopezyDoffoTP.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.undef.PerezLopezyDoffoTP.data.model.User
import com.undef.PerezLopezyDoffoTP.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadUser() {
        val userId = UserRepository.getCurrentUserId()
        if (userId == null) {
            _user.value = null
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            runCatching {
                UserRepository.getUser(userId)
            }.onSuccess { fetchedUser ->
                _user.value = fetchedUser
            }.onFailure { error ->
                _errorMessage.value = error.message ?: "No se pudo cargar el perfil"
            }
            _isLoading.value = false
        }
    }

    fun logout() {
        UserRepository.logout()
        _user.value = null
    }
}
