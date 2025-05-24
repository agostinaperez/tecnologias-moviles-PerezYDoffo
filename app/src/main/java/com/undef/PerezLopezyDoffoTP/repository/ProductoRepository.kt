package com.undef.PerezLopezyDoffoTP.repository

import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento
import com.undef.PerezLopezyDoffoTP.data.model.Producto

object ProductoRepository {
    private val productos = mutableListOf(
        // 1-5: Pastelería saludable (id = 1)
        Producto(1, "Torta de zanahoria sin azúcar", listOf(
            "https://source.unsplash.com/1",
            "https://source.unsplash.com/2",
            "https://source.unsplash.com/3"
        ), 1, "Torta saludable con dátiles y nueces.", false),
        Producto(2, "Brownies veganos", listOf(
            "https://source.unsplash.com/4",
            "https://source.unsplash.com/5",
            "https://source.unsplash.com/6"
        ), 1, "Brownies con cacao puro y harina de almendras.", false),
        Producto(3, "Cookies integrales", listOf(
            "https://source.unsplash.com/7",
            "https://source.unsplash.com/8",
            "https://source.unsplash.com/9"
        ), 1, "Galletas crocantes con avena y chía.", false),
        Producto(4, "Budín de banana", listOf(
            "https://source.unsplash.com/10",
            "https://source.unsplash.com/11",
            "https://source.unsplash.com/12"
        ), 1, "Budín sin azúcar refinada, ideal para desayunos.", false),
        Producto(5, "Barritas energéticas", listOf(
            "https://source.unsplash.com/13",
            "https://source.unsplash.com/14",
            "https://source.unsplash.com/15"
        ), 1, "Hechas con frutos secos y semillas.", false),

        // 6-10: Moda circular (id = 2)
        Producto(6, "Campera reciclada", listOf(
            "https://source.unsplash.com/16",
            "https://source.unsplash.com/17",
            "https://source.unsplash.com/18"
        ), 2, "Campera de materiales reutilizados.", false),
        Producto(7, "Remera ecológica", listOf(
            "https://source.unsplash.com/19",
            "https://source.unsplash.com/20",
            "https://source.unsplash.com/21"
        ), 2, "Remera hecha con algodón orgánico.", false),
        Producto(8, "Bolso de jean reciclado", listOf(
            "https://source.unsplash.com/22",
            "https://source.unsplash.com/23",
            "https://source.unsplash.com/24"
        ), 2, "Hecho a mano con jeans reutilizados.", false),
        Producto(9, "Sombrero bohemio", listOf(
            "https://source.unsplash.com/25",
            "https://source.unsplash.com/26",
            "https://source.unsplash.com/27"
        ), 2, "Accesorio de moda reciclada.", false),
        Producto(10, "Pantalón patchwork", listOf(
            "https://source.unsplash.com/28",
            "https://source.unsplash.com/29",
            "https://source.unsplash.com/30"
        ), 2, "Diseño único con retazos de telas.", false),

        // 11-15: Cerámica para principiantes (id = 3)
        Producto(11, "Taza artesanal", listOf(
            "https://source.unsplash.com/31",
            "https://source.unsplash.com/32",
            "https://source.unsplash.com/33"
        ), 3, "Taza hecha a mano para café o té.", false),
        Producto(12, "Set de desayuno", listOf(
            "https://source.unsplash.com/34",
            "https://source.unsplash.com/35",
            "https://source.unsplash.com/36"
        ), 3, "Plato, taza y bowl en cerámica esmaltada.", false),
        Producto(13, "Curso de cerámica básica", listOf(
            "https://source.unsplash.com/37",
            "https://source.unsplash.com/38",
            "https://source.unsplash.com/39"
        ), 3, "Curso online para principiantes.", false),
        Producto(14, "Jarrón decorativo", listOf(
            "https://source.unsplash.com/40",
            "https://source.unsplash.com/41",
            "https://source.unsplash.com/42"
        ), 3, "Jarrón con diseño minimalista.", false),
        Producto(15, "Kit para moldear arcilla", listOf(
            "https://source.unsplash.com/43",
            "https://source.unsplash.com/44",
            "https://source.unsplash.com/45"
        ), 3, "Incluye arcilla, cortadores y estecas.", false),

        // 16-20: Cosmética Natural (id = 4)
        Producto(16, "Bálsamo labial natural", listOf(
            "https://source.unsplash.com/46",
            "https://source.unsplash.com/47",
            "https://source.unsplash.com/48"
        ), 4, "Con cera de abejas y aceite de coco.", false),
        Producto(17, "Jabón artesanal de lavanda", listOf(
            "https://source.unsplash.com/49",
            "https://source.unsplash.com/50",
            "https://source.unsplash.com/51"
        ), 4, "Calmante y suave para la piel.", false),
        Producto(18, "Crema hidratante con caléndula", listOf(
            "https://source.unsplash.com/52",
            "https://source.unsplash.com/53",
            "https://source.unsplash.com/54"
        ), 4, "Especial para pieles sensibles.", false),
        Producto(19, "Aceite esencial de romero", listOf(
            "https://source.unsplash.com/55",
            "https://source.unsplash.com/56",
            "https://source.unsplash.com/57"
        ), 4, "Estimula la circulación y relaja.", false),
        Producto(20, "Desodorante sólido natural", listOf(
            "https://source.unsplash.com/58",
            "https://source.unsplash.com/59",
            "https://source.unsplash.com/60"
        ), 4, "Sin aluminio, con bicarbonato y coco.", false)
    )

    fun getProductos(): List<Producto>{
        return productos
    }
    fun getProductosFiltrados(searchQuery: String): List<Producto> {
        return getProductos().filter { it.name.contains(searchQuery, ignoreCase = true) }
    }
    fun getProductoById(productoId: Number): Producto {
        return getProductos().find { it.id == productoId } ?: Producto(0, "", listOf(""), 0, "", false)
    }
    fun getEmprendimientoByProducto(producto: Producto): Emprendimiento{
        return EmprendimientoRepository.getEmprendimientoById(producto.idEmprendimiento)
    }

    fun getFavs(): List<Producto> {
        return getProductos().filter { it.isFav }
    }
    fun setFav(productoId: Number) {
        val producto = getProductoById(productoId)
        producto.isFav = !producto.isFav
    }
}