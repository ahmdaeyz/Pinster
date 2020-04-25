package dev.ahmdaeyz.pinster.data.network

import dev.ahmdaeyz.pinster.data.db.entities.Source
import dev.ahmdaeyz.pinster.domain.common.enums.NewsAPICategory
import io.reactivex.Single

interface NewsSourcesNetworkDataSource {
    fun getSources(category: NewsAPICategory, langCode: String = "en", countryCode: String = "us"): Single<List<Source>>
}