package com.undef.PerezLopezyDoffoTP.data.local.entity

import androidx.room.Entity

/**
 * Tabla intermedia que guarda los IDs de emprendimientos que un usuario marcó como favorito pa tenerlos diferenciados x usuario */
@Entity(
    tableName = "user_favorites",
    primaryKeys = ["userId", "emprendimientoId"]
)
data class UserFavoriteEntity(
    val userId: Int,
    val emprendimientoId: Int
)
