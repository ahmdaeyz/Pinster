package dev.ahmdaeyz.pinster.data.network.response


import dev.ahmdaeyz.pinster.data.db.entities.Article

data class NewsArticles(
    val articles: List<Article>?,
    val status: String?,
    val totalResults: Int?
)