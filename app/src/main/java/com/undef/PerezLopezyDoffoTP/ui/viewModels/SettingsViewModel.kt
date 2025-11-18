package com.undef.PerezLopezyDoffoTP.ui.viewModels

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.undef.PerezLopezyDoffoTP.repository.BiometricAuthManager

class SettingsViewModel: ViewModel() {
    // Notificaciones
    var notificationsEnabled by mutableStateOf(true)
    var expandedNotification by mutableStateOf(false)

    fun toggleNotifications(enabled: Boolean){
        notificationsEnabled = enabled
    }
    // Preferencias
    var expandedPreferences by mutableStateOf(false)
    val preferences = listOf("Experiencias", "Comida", "Artículos")
    val selectedPreferences = mutableStateListOf<String>()

    // Permissions
    var isLocationEnabled by mutableStateOf(false)
    var isBiometricEnabled by mutableStateOf(false)

    fun checkLocationPermission(context: Context): Boolean{
        val fineLocationPermission = ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        return fineLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    fun loadPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        isLocationEnabled = sharedPreferences.getBoolean("isLocationEnabled", false)
        notificationsEnabled = sharedPreferences.getBoolean("notificationsEnabled", true)
        selectedPreferences.clear()
        selectedPreferences.addAll(sharedPreferences.getStringSet("selectedPreferences", emptySet()) ?: emptySet())
        isBiometricEnabled = BiometricAuthManager.isEnabled()
    }

    fun savePreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("isLocationEnabled", isLocationEnabled)
            putBoolean("notificationsEnabled", notificationsEnabled)
            putStringSet("selectedPreferences", selectedPreferences.toSet())
            putBoolean("isBiometricEnabled", isBiometricEnabled)
            apply()
        }
    }

    fun updateBiometricEnabled(enabled: Boolean) {
        isBiometricEnabled = enabled
        BiometricAuthManager.setEnabled(enabled)
    }

    /*fun requestLocationPermission(context: Context, onPermissionResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isGranted = checkLocationPermission(context)
            if (isGranted) {
                Toast.makeText(context, "Permiso de ubicación ya concedido", Toast.LENGTH_SHORT).show()
                isLocationEnabled = true
            } else {
                onPermissionResult(false)
            }
        }
    }*/
}
