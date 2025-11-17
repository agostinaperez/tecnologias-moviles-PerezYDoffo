package com.undef.PerezLopezyDoffoTP.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.undef.PerezLopezyDoffoTP.data.local.dao.EmprendimientoDao
import com.undef.PerezLopezyDoffoTP.data.local.entity.EmprendimientoEntity
import com.undef.PerezLopezyDoffoTP.data.local.entity.UserFavoriteEntity

@Database(
    entities = [EmprendimientoEntity::class, UserFavoriteEntity::class],
    version = 2,
    exportSchema = false
)

abstract class ManosLocalesDatabase : RoomDatabase() {
    abstract fun emprendimientoDao(): EmprendimientoDao

    companion object {
        /**
         * Agrega la tabla que almacena favoritos por usuario.
         */
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS user_favorites (
                        userId INTEGER NOT NULL,
                        emprendimientoId INTEGER NOT NULL,
                        PRIMARY KEY(userId, emprendimientoId)
                    )
                    """.trimIndent()
                )
            }
        }
    }
}
