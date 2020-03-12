package com.raydevelopers.trendinggitrepos.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.raydevelopers.trendinggitrepos.model.TrendingRepositoryListObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [TrendingRepositoryListObject::class], version = 1, exportSchema = false)
@TypeConverters(DataTypeConverter::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun trendingDao(): TrendingDao
    class TrendingRepoDatabaseCallback(   private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.trendingDao())
                }
            }
        }

        suspend fun populateDatabase(trendingDao: TrendingDao) {
            // TODO: Add your own words!

        }
    }
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context,scope: CoroutineScope): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "trending_database"
                ).addCallback(TrendingRepoDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
