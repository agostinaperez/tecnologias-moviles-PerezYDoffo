package com.undef.PerezLopezyDoffoTP.data.model

data class Emprendedor(
    val id: Int,
    val name: String,
    val bio: String,
    val location: String,
    val image: String,
    val website: String,
    val contactMethods: List<ContactMethod> = emptyList()
)

data class ContactMethod(
    val type: ContactMethodType,
    val value: String,
    val label: String? = null
)

enum class ContactMethodType {
    PHONE,
    WHATSAPP,
    INSTAGRAM,
    EMAIL,
    WEBSITE,
    FACEBOOK,
    OTHER
}
