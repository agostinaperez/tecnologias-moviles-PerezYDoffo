package com.undef.PerezLopezyDoffoTP.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento

class FavsViewModel: ViewModel() {
    private val emprendimientos = Emprendimiento
    private val _favorites = MutableLiveData(emprendimientos.getFavs())
    val favorites: LiveData<List<Emprendimiento>> = _favorites

    fun getEmprendimientosFavs(): List<Emprendimiento>{
        return Emprendimiento.getFavs()
    }
    /*fun toggleFav(emprendimientoId: Number) {
        // Modifica la lista de emprendimientos
        val updatedEmprendimientos = _favorites.value?.map { emprendimiento ->
            if (emprendimiento.id == emprendimientoId) {
                emprendimiento.copy(isFav = !emprendimiento.isFav) // alterna el estado de favorito
            } else {
                emprendimiento
            }
        }

        // Actualiza la lista de emprendimientos
        _favorites.value = updatedEmprendimientos
        emprendimientos.setFav(emprendimientoId)
    }*/
}