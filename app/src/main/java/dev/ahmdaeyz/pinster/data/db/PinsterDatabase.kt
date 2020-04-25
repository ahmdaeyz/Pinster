package dev.ahmdaeyz.pinster.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.ahmdaeyz.pinster.data.db.daos.NewsSourcesDao
import dev.ahmdaeyz.pinster.data.db.entities.Source

@Database(
        entities = [Source::class],
        version = 1
)
abstract class PinsterDatabase : RoomDatabase() {
    abstract fun newsSourcesDao(): NewsSourcesDao

    companion object {
        @Volatile
        private var instance: PinsterDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        PinsterDatabase::class.java,
                        "pinster.db"
                ).build()
    }
}