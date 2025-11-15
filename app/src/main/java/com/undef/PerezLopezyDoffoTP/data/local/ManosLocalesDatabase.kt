package com.undef.PerezLopezyDoffoTP.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.undef.PerezLopezyDoffoTP.data.local.dao.EmprendimientoDao
import com.undef.PerezLopezyDoffoTP.data.local.entity.EmprendimientoEntity

@Database(
    entities = [EmprendimientoEntity::class],
    version = 1,
    exportSchema = false
)

abstract class ManosLocalesDatabase : RoomDatabase() {
    abstract fun emprendimientoDao(): EmprendimientoDao
}
