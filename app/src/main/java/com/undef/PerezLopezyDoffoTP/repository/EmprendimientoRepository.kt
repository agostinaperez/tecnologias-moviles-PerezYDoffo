package com.undef.PerezLopezyDoffoTP.repository

import com.undef.PerezLopezyDoffoTP.data.model.Emprendedor
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento

object EmprendimientoRepository{
    private val emprendedores = listOf(
        Emprendedor(
            id = 1,
            name = "Cerámica by Sofi",
            bio = "Piezas hechas a mano con terminaciones suaves y resistentes.",
            location = "A 3 km",
            image = "https://plus.unsplash.com/premium_photo-1706115464365-a82276f7e7b4?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            website = "www.ceramicabysofi.com.ar"
        ),
        Emprendedor(
            id = 2,
            name = "Sabores Saludables",
            bio = "Pastelería sin conservantes con opciones integrales y veganas.",
            location = "A 20 km",
            image = "https://images.unsplash.com/photo-1534432182912-63863115e106?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            website = "www.saboressaludables.com.ar"
        ),
        Emprendedor(
            id = 3,
            name = "Moda Circular",
            bio = "Ropa curada y reciclada para extender la vida útil de cada prenda.",
            location = "A 10 km",
            image = "https://plus.unsplash.com/premium_photo-1714347049254-9ab68ae6a8df?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            website = "www.modacircular.com.ar"
        ),
        Emprendedor(
            id = 4,
            name = "Cosmética Viva",
            bio = "Línea de cosmética natural y sustentable elaborada a pequeña escala.",
            location = "A 6 km",
            image = "https://images.unsplash.com/photo-1624454002302-36b824d7bd0a?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            website = "www.cosmeticaviva.com.ar"
        )
    )

    private val emprendimientos = mutableListOf(
        Emprendimiento(
            id = 1,
            name = "Caja de pastelería saludable",
            description = "Selección de mini tortas sin azúcar procesada, ideales para regalar.",
            image = "https://images.unsplash.com/photo-1534432182912-63863115e106?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            category = "Pastelería",
            emprendedor = emprendedores[1]
        ),
        Emprendimiento(
            id = 2,
            name = "Tarta integral de frutos rojos",
            description = "Base de harina integral rellena con frutos frescos de estación.",
            image = "https://images.unsplash.com/photo-1504753793650-d4a2b783c15e?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8ZGlldCUyMGRlc3NlcnR8ZW58MHx8MHx8fDA%3D",
            category = "Pastelería",
            emprendedor = emprendedores[1]
        ),
        Emprendimiento(
            id = 3,
            name = "Set de mate de cerámica",
            description = "Incluye mate, cucharita y platito esmaltados en tonos pastel.",
            image = "https://plus.unsplash.com/premium_photo-1706115464365-a82276f7e7b4?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            category = "Cerámica",
            emprendedor = emprendedores[0]
        ),
        Emprendimiento(
            id = 4,
            name = "Platos irregulares esmaltados",
            description = "Juego de dos platos medianos, aptos para lavavajillas.",
            image = "https://images.unsplash.com/photo-1504674900247-0877df9cc836?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8Y2VyYW1pY3N8ZW58MHx8MHx8fDA%3D",
            category = "Cerámica",
            emprendedor = emprendedores[0]
        ),
        Emprendimiento(
            id = 5,
            name = "Kit cápsula de verano",
            description = "Camisa oversize + short de lino recuperado, edición limitada.",
            image = "https://plus.unsplash.com/premium_photo-1714347049254-9ab68ae6a8df?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            category = "Ropa",
            emprendedor = emprendedores[2]
        ),
        Emprendimiento(
            id = 6,
            name = "Rutina facial botánica",
            description = "Limpiador suave, tónico hidratante y sérum reparador.",
            image = "https://images.unsplash.com/photo-1624454002302-36b824d7bd0a?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            category = "Cosmética",
            emprendedor = emprendedores[3]
        ),
        Emprendimiento(
            id = 7,
            name = "Roll-on relajante",
            description = "Blend de aceites esenciales para llevar siempre encima.",
            image = "https://images.unsplash.com/photo-1487412720507-e7ab37603c6f?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OXx8YXJvbWF0aGVyYXB5fGVufDB8fDB8fHww",
            category = "Cosmética",
            emprendedor = emprendedores[3]
        )
    )

    fun getEmprendimientos(): List<Emprendimiento> {
        return emprendimientos
    }

    fun getEmprendimientosFiltrados(searchQuery: String): List<Emprendimiento> {
        return if (searchQuery.isBlank()) {
            getEmprendimientos()
        } else {
            getEmprendimientos().filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.category.contains(searchQuery, ignoreCase = true) ||
                        it.emprendedor.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    fun getEmprendimientoById(emprendimientoId: Int): Emprendimiento? {
        return getEmprendimientos().find { it.id == emprendimientoId }
    }

    fun getFavs(): List<Emprendimiento>{
        return getEmprendimientos().filter { it.isFav }
    }

    fun setFav(emprendimientoId: Int){
        val emprendimiento = getEmprendimientoById(emprendimientoId) ?: return
        emprendimiento.isFav = !emprendimiento.isFav
    }

    fun getEmprendimientosDelEmprendedor(emprendedorId: Int, excludeEmprendimientoId: Int? = null): List<Emprendimiento> {
        return getEmprendimientos().filter { emprendimiento ->
            emprendimiento.emprendedor.id == emprendedorId && emprendimiento.id != excludeEmprendimientoId
        }
    }
}
