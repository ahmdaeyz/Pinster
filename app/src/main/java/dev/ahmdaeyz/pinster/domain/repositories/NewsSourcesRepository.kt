package dev.ahmdaeyz.pinster.domain.repositories

import dev.ahmdaeyz.pinster.data.db.entities.Source
import io.reactivex.Single

interface NewsSourcesRepository {
    fun getSourcesFor(userCategories: MutableSet<String>): Single<MutableList<Source>>
    fun getLocalSources(): Single<List<Source>>
    fun initializeSources(userCategories: MutableSet<String>)
}