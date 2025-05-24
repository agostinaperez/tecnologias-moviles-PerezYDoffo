package com.undef.PerezLopezyDoffoTP.repository

import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento

object EmprendimientoRepository{
    private val emprendimientos = mutableListOf(
        Emprendimiento(
            1,
            "Pastelería saludable",
            "A 20 km",
            "https://images.unsplash.com/photo-1534432182912-63863115e106?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            "Pastelería",
            "www.example.com.ar"
        ),
        Emprendimiento(
            2,
            "Moda circular",
            "A 10 km",
            "https://plus.unsplash.com/premium_photo-1714347049254-9ab68ae6a8df?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            "Ropa",
            "www.example.com.ar"
        ),
        Emprendimiento(
            3,
            "Cerámica para principiantes",
            "A 3 km",
            "https://plus.unsplash.com/premium_photo-1706115464365-a82276f7e7b4?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            "Cerámica",
            "www.example.com.ar"
        ),
        Emprendimiento(
            4,
            "Cosmética Natural",
            "A 6 km",
            "https://images.unsplash.com/photo-1624454002302-36b824d7bd0a?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            "Cosmética",
            "www.example.com.ar"
        ))
    fun getEmprendimientos(): List<Emprendimiento> {
        return emprendimientos
    }

    fun getEmprendimientosFiltrados(searchQuery: String): List<Emprendimiento> {
        return getEmprendimientos().filter { it.name.contains(searchQuery, ignoreCase = true) || it.categoria.contains(searchQuery, ignoreCase = true) }
    }

    fun getEmprendimientoById(emprendimientoId: Number): Emprendimiento {
        return getEmprendimientos().find { it.id == emprendimientoId } ?: Emprendimiento(0, "", "", "", "", "")
    }
}