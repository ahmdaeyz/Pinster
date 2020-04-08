package dev.ahmdaeyz.pinster.data.network.response


import dev.ahmdaeyz.pinster.data.db.entities.Source

data class NewsSources(
    val sources: List<Source>?,
    val status: String?
)