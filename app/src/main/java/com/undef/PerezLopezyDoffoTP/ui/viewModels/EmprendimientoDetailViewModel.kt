package com.undef.PerezLopezyDoffoTP.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento
import com.undef.PerezLopezyDoffoTP.repository.EmprendimientoRepository
import com.undef.PerezLopezyDoffoTP.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EmprendimientoDetailViewModel(
    private val emprendimientoId: Int
) : ViewModel() {

    val emprendimiento: StateFlow<Emprendimiento?> =
        EmprendimientoRepository.observeEmprendimiento(emprendimientoId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )

    val otrosProductos: StateFlow<List<Emprendimiento>> =
        emprendimiento.flatMapLatest { current ->
            if (current == null) {
                flowOf(emptyList())
            } else {
                EmprendimientoRepository.observeEmprendimientosDelEmprendedor(
                    current.emprendedor.id,
                    excludeEmprendimientoId = current.id
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            EmprendimientoRepository.refreshEmprendimiento(emprendimientoId)
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val userId = UserRepository.getCurrentUserId() ?: return@launch
            EmprendimientoRepository.setFav(emprendimientoId, userId)
        }
    }

    companion object {
        fun provideFactory(emprendimientoId: Int): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return EmprendimientoDetailViewModel(emprendimientoId) as T
                }
            }
    }
}
