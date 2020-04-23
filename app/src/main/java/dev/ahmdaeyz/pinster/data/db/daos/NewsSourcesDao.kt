package dev.ahmdaeyz.pinster.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.ahmdaeyz.pinster.data.db.entities.Source
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface NewsSourcesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSources(vararg sources: Source): Completable

    @Query("SELECT * FROM user_sources WHERE category = :category")
    fun getUserSources(category: String?): Single<List<Source>>
}