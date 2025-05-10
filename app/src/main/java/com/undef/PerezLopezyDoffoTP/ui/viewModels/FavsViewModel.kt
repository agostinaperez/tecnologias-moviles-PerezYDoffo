package com.undef.PerezLopezyDoffoTP.ui.viewModels

import androidx.lifecycle.ViewModel
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento
import com.undef.PerezLopezyDoffoTP.repository.EmprendimientoRepository

class FavsViewModel: ViewModel() {

    fun getEmprendimientosFavs(): List<Emprendimiento>{
        return EmprendimientoRepository.getFavs()
    }
}