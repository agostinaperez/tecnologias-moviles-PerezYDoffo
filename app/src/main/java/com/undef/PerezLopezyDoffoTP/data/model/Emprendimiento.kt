package com.undef.PerezLopezyDoffoTP.data.model

data class Emprendimiento (
    val id: Number,
    val name: String,
    val location: String,
    val image: String,
    val producto: String,
    val website: String,
    var isFav: Boolean
)