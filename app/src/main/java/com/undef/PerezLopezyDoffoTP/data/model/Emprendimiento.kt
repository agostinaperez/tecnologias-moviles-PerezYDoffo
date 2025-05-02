package com.undef.PerezLopezyDoffoTP.data.model

data class Emprendimiento (
    val name: String,
    val location: String,
    val image: String,
    val tel: Long,
    val website: String
) {
    companion object {
        fun getEmprendimientos(): List<Emprendimiento> {
            return listOf(
                Emprendimiento(
                    "Pastelería saludable",
                    "A 20 km",
                    "https://unsplash.com/es/fotos/masa-horneada-en-bol-n49BjsFf5dI",
                    3512007574,
                    "www.example.com.ar"
                ),
                Emprendimiento(
                    "Moda circular",
                    "A 10 km",
                    "https://unsplash.com/es/fotos/un-hombre-y-una-mujer-mirando-vestidos-en-un-perchero-3JfydSV5Ojs",
                    343268781,
                    "www.example.com.ar"
                ),
                Emprendimiento(
                    "Cerámica para principiantes",
                    "A 3 km",
                    "https://unsplash.com/es/fotos/una-persona-esta-trabajando-en-ceramica-sobre-una-mesa-7eKcY6Vffqs",
                    5789238430,
                    "www.example.com.ar"
                ))
        }
    }
}