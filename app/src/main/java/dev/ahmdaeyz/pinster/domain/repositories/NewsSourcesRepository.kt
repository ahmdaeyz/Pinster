package dev.ahmdaeyz.pinster.domain.repositories

import dev.ahmdaeyz.pinster.data.db.entities.Source
import dev.ahmdaeyz.pinster.domain.common.enums.UserCategory
import io.reactivex.Single

interface NewsSourcesRepository {
    fun getSourcesFor(category: UserCategory): Single<List<Source>>
}