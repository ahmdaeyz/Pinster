package dev.ahmdaeyz.pinster.data.repositories

import android.util.Log
import dev.ahmdaeyz.pinster.data.db.daos.NewsSourcesDao
import dev.ahmdaeyz.pinster.data.db.entities.Source
import dev.ahmdaeyz.pinster.data.network.NewsSourcesNetworkDataSource
import dev.ahmdaeyz.pinster.domain.common.enums.NewsAPICategory
import dev.ahmdaeyz.pinster.domain.common.enums.UserCategory
import dev.ahmdaeyz.pinster.domain.common.enums.categoryMapping
import dev.ahmdaeyz.pinster.domain.repositories.NewsSourcesRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class NewsSourcesRepositoryImpl(
        private val newsSourcesNetworkDataSource: NewsSourcesNetworkDataSource,
        private val newsSourcesDao: NewsSourcesDao
) : NewsSourcesRepository {

    override fun getSourcesFor(userCategories: MutableSet<String>): Single<MutableList<Source>> {
        val categories = userCategories.map { userCategory ->
            (categoryMapping[UserCategory.valueOf(userCategory.toUpperCase(Locale.ROOT))]
                    ?: error("")).value
        }.filter {
            it != ""
        }.toSet()
        Log.i("TheSources", "_categories $categories")
        Log.i("TheSources", NewsAPICategory.valueOf(categories.toList()[0].toUpperCase(Locale.ROOT)).value)
        return Observable.fromIterable(categories)
                .concatMap { category ->
                    newsSourcesNetworkDataSource.getSources(
                            NewsAPICategory.valueOf(category.toUpperCase(Locale.ROOT).replace(" ", "_"))
                    ).toObservable()
                }
                .collectInto(mutableListOf<Source>()) { mutableList: MutableList<Source>, list1: List<Source> ->
                    mutableList.addAll(list1)
                }.flatMap { sourcesList ->
                    newsSourcesDao.insertSources(*sourcesList.toTypedArray())
                            .andThen(Single.fromObservable(Observable.just(sourcesList)))
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getLocalSources(): Single<List<Source>> {
        return newsSourcesDao.getUserSources()
    }

    override fun initializeSources(userCategories: MutableSet<String>) {
        getSourcesFor(userCategories)
                .subscribe()
    }
}
