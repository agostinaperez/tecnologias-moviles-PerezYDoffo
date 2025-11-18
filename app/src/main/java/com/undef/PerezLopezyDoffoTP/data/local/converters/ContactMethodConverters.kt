package com.undef.PerezLopezyDoffoTP.data.local.converters

import androidx.room.TypeConverter
import com.undef.PerezLopezyDoffoTP.data.model.ContactMethod
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class ContactMethodListConverter {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val type = Types.newParameterizedType(List::class.java, ContactMethod::class.java)
    private val adapter = moshi.adapter<List<ContactMethod>>(type)

    @TypeConverter
    fun fromJson(value: String?): List<ContactMethod> {
        if (value.isNullOrBlank()) return emptyList()
        return runCatching { adapter.fromJson(value) }.getOrNull().orEmpty()
    }

    @TypeConverter
    fun toJson(value: List<ContactMethod>?): String {
        return adapter.toJson(value ?: emptyList())
    }
}
