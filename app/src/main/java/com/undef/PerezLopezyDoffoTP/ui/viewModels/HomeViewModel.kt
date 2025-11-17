package com.undef.PerezLopezyDoffoTP.ui.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento
import com.undef.PerezLopezyDoffoTP.repository.EmprendimientoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val emprendimientos: StateFlow<List<Emprendimiento>> =
        EmprendimientoRepository.observeEmprendimientos()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    private val _locationPermissionGranted = MutableStateFlow(false)
    val locationPermissionGranted: StateFlow<Boolean> = _locationPermissionGranted

    init {
        viewModelScope.launch {
            EmprendimientoRepository.refreshEmprendimientos()
        }
    }

    fun setLocationPermission(granted: Boolean) {
        _locationPermissionGranted.value = granted
    }
}
