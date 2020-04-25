package dev.ahmdaeyz.pinster.data.network

import dev.ahmdaeyz.pinster.data.db.entities.Source
import dev.ahmdaeyz.pinster.domain.common.enums.NewsAPICategory
import io.reactivex.Single

class NewsSourcesNetworkDataSourceImpl(
        private val newsAPIApiService: NewsAPIApiService
) : NewsSourcesNetworkDataSource {

    override fun getSources(category: NewsAPICategory, langCode: String, countryCode: String): Single<List<Source>> {
        return newsAPIApiService.getSources(category.value, countryCode = "us", languageCode = "en")
                .map { it.sources }
    }
}