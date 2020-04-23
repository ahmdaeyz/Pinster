package dev.ahmdaeyz.pinster.data.repositories

import android.util.Log
import dev.ahmdaeyz.pinster.data.db.daos.NewsSourcesDao
import dev.ahmdaeyz.pinster.data.db.entities.Source
import dev.ahmdaeyz.pinster.data.network.NewsSourcesNetworkDataSource
import dev.ahmdaeyz.pinster.domain.common.enums.UserCategory
import dev.ahmdaeyz.pinster.domain.common.enums.categoryMapping
import dev.ahmdaeyz.pinster.domain.repositories.NewsSourcesRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsSourcesRepositoryImpl(
        private val newsSourcesNetworkDataSource: NewsSourcesNetworkDataSource,
        private val newsSourcesDao: NewsSourcesDao
) : NewsSourcesRepository {

    override fun getSourcesFor(category: UserCategory): Single<List<Source>> {
        val newsAPICategory = categoryMapping[category]
        return newsSourcesDao.getUserSources(newsAPICategory?.value)
                .onErrorResumeNext(
                        newsSourcesNetworkDataSource.getSources(
                                newsAPICategory!!,
                                "en",
                                "us"
                        ).doOnSuccess {
                            newsSourcesDao.insertSources(*it.toTypedArray())
                                    .doOnError {
                                        throw it
                                    }.doOnComplete {
                                        Log.i("NewsSourcesRepository", "Cached Successfully")
                                    }.subscribeOn(Schedulers.io())
                                    .subscribe()
                        }
                )
                .doOnSuccess {
                    Log.i("NewsSourcesRepository", "Fetched Sources Successfully")
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }


}