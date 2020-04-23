package dev.ahmdaeyz.pinster.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_sources")
data class Source(
        val category: String?,
        val country: String?,
        val description: String?,
        @PrimaryKey
        val id: String,
        val language: String?,
        val name: String?,
        val url: String?
)