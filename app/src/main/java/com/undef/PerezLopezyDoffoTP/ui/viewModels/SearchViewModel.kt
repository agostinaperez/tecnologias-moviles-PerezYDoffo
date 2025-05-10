package com.undef.PerezLopezyDoffoTP.ui.viewModels
import androidx.lifecycle.ViewModel
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento
import com.undef.PerezLopezyDoffoTP.repository.EmprendimientoRepository


class SearchViewModel : ViewModel(){
    fun getEmprendimientosFiltrados(title:String): List<Emprendimiento>{
        return EmprendimientoRepository.getEmprendimientosFiltrados(title)
    }
}