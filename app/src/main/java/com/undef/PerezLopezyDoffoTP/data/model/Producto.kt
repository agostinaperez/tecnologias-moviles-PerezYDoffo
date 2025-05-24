package com.undef.PerezLopezyDoffoTP.data.model

data class Producto (
    val id: Number,
    val name: String,
    val image: List<String>,
    val idEmprendimiento: Number,
    val description: String,
    var isFav: Boolean
)