package com.undef.PerezLopezyDoffoTP.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.undef.PerezLopezyDoffoTP.data.model.Emprendedor
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento

@Entity(tableName = "emprendimientos")
data class EmprendimientoEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val category: String,
    @Embedded(prefix = "emprendedor_")
    val emprendedor: Emprendedor,
    val isFav: Boolean
)

fun EmprendimientoEntity.toDomain(): Emprendimiento = Emprendimiento(
    id = id,
    name = name,
    description = description,
    image = image,
    category = category,
    emprendedor = emprendedor,
    isFav = isFav
)

fun Emprendimiento.toEntity(): EmprendimientoEntity = EmprendimientoEntity(
    id = id,
    name = name,
    description = description,
    image = image,
    category = category,
    emprendedor = emprendedor,
    isFav = isFav
)
