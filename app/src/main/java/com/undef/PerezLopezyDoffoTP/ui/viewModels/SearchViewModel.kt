package com.undef.PerezLopezyDoffoTP.ui.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento
import com.undef.PerezLopezyDoffoTP.repository.EmprendimientoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SearchViewModel : ViewModel() {
    private val query = MutableStateFlow("")

    val searchQuery: StateFlow<String> = query

    private val emprendimientosSource = EmprendimientoRepository.observeEmprendimientos()

    val emprendimientos: StateFlow<List<Emprendimiento>> =
        combine(query, emprendimientosSource) { text, emprendimientos ->
            val normalizedQuery = text.trim()
            if (normalizedQuery.isEmpty()) {
                emprendimientos
            } else {
                val lowerQuery = normalizedQuery.lowercase()
                emprendimientos.filter { emprendimiento ->
                    emprendimiento.name.contains(lowerQuery, ignoreCase = true) ||
                        emprendimiento.emprendedor.name.contains(lowerQuery, ignoreCase = true)
                }
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun updateQuery(value: String) {
        query.value = value
    }
}
