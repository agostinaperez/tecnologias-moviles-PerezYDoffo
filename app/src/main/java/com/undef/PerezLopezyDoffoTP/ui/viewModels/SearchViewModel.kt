package com.undef.PerezLopezyDoffoTP.ui.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento
import com.undef.PerezLopezyDoffoTP.repository.EmprendimientoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class SearchViewModel : ViewModel() {
    private val query = MutableStateFlow("")

    val searchQuery: StateFlow<String> = query

    val emprendimientos: StateFlow<List<Emprendimiento>> =
        query
            .debounce(250)
            .flatMapLatest { text ->
                EmprendimientoRepository.observeFiltered(text.trim())
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
