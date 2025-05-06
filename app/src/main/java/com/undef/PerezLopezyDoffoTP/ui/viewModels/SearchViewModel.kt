package com.undef.PerezLopezyDoffoTP.ui.viewModels
import androidx.lifecycle.ViewModel
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento


class SearchViewModel : ViewModel(){
    fun getEmprendimientosFiltrados(title:String): List<Emprendimiento>{
        return Emprendimiento.getEmprendimientosFiltrados(title)
    }
}