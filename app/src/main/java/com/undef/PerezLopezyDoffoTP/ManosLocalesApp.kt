package com.undef.PerezLopezyDoffoTP

import android.app.Application
import com.undef.PerezLopezyDoffoTP.repository.EmprendimientoRepository
import com.undef.PerezLopezyDoffoTP.repository.UserRepository

class ManosLocalesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        EmprendimientoRepository.initialize(this)
        UserRepository.initialize(this)
    }
}
