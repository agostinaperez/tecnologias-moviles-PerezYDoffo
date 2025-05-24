package com.undef.PerezLopezyDoffoTP.ui.viewModels

import androidx.lifecycle.ViewModel
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento
import com.undef.PerezLopezyDoffoTP.data.model.Producto
import com.undef.PerezLopezyDoffoTP.repository.EmprendimientoRepository
import com.undef.PerezLopezyDoffoTP.repository.ProductoRepository

class FavsViewModel: ViewModel() {

    fun getProductosFavs(): List<Producto>{
        return ProductoRepository.getFavs()
    }
}