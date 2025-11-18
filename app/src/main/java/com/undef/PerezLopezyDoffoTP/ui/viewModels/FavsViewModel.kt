package com.undef.PerezLopezyDoffoTP.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento
import com.undef.PerezLopezyDoffoTP.repository.EmprendimientoRepository
import com.undef.PerezLopezyDoffoTP.repository.FavoriteAlertsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class FavoriteAlertItem(
    val emprendimiento: Emprendimiento,
    val alertsEnabled: Boolean
)

class FavsViewModel : ViewModel() {
    private val favoritesFlow: Flow<List<Emprendimiento>> =
        EmprendimientoRepository.observeFavorites()

    val favoriteItems: StateFlow<List<FavoriteAlertItem>> =
        favoritesFlow
            .combine(FavoriteAlertsRepository.alertedEntrepreneurs) { favorites, alerts ->
                favorites.map { emprendimiento ->
                    FavoriteAlertItem(
                        emprendimiento = emprendimiento,
                        alertsEnabled = alerts.contains(emprendimiento.emprendedor.id)
                    )
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun setAlertFor(emprendedorId: Int, enabled: Boolean) {
        FavoriteAlertsRepository.setAlert(emprendedorId, enabled)
    }
}
