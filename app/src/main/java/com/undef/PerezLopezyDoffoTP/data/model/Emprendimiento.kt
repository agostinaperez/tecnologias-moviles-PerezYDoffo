package com.undef.PerezLopezyDoffoTP.data.model

data class Emprendimiento(
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val category: String,
    val emprendedor: Emprendedor,
    var isFav: Boolean = false
)
