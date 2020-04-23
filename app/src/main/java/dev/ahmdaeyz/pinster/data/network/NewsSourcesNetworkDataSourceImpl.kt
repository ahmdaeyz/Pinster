package dev.ahmdaeyz.pinster.data.network

import android.util.Log
import dev.ahmdaeyz.pinster.data.db.entities.Source
import dev.ahmdaeyz.pinster.domain.common.enums.NewsAPICategory
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class NewsSourcesNetworkDataSourceImpl(
        val newsAPIApiService: NewsAPIApiService
) : NewsSourcesNetworkDataSource {

    override fun getSources(category: NewsAPICategory, langCode: String, countryCode: String): Single<List<Source>> {
        return newsAPIApiService.getSources(category.value, countryCode = "us", languageCode = "en")
                .subscribeOn(Schedulers.io())
                .map {
                    Log.i("NewsSourceNetwork", "This got called")
                    it.sources
                }
    }
}