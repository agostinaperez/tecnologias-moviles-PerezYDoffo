package com.undef.PerezLopezyDoffoTP.ui.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento
import com.undef.PerezLopezyDoffoTP.repository.EmprendimientoRepository

class HomeViewModel : ViewModel() {
    private val _emprendimientos = MutableLiveData(EmprendimientoRepository.getEmprendimientos())
    val emprendimientos: MutableLiveData<List<Emprendimiento>> = _emprendimientos
}