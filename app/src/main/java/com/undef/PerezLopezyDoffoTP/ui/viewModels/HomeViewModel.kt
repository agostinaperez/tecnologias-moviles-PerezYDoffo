package com.undef.PerezLopezyDoffoTP.ui.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento

class HomeViewModel : ViewModel() {
    private val _emprendimientos = MutableLiveData(Emprendimiento.getEmprendimientos())
    val emprendimientos: LiveData<List<Emprendimiento>> = _emprendimientos

    fun searchEmprendimientos(query: String) {
        // Lógica para filtrar eventos según el query
        _emprendimientos.value = _emprendimientos.value?.filter { it.name.contains(query, ignoreCase = true) }
    }
}