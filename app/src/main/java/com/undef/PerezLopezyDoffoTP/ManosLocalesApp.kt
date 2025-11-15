package com.undef.PerezLopezyDoffoTP

import android.app.Application
import com.undef.PerezLopezyDoffoTP.repository.EmprendimientoRepository

class ManosLocalesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        EmprendimientoRepository.initialize(this)
    }
}
