package com.alien.ward.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.alien.ward.database.dao.MyDao
import com.alien.ward.database.entity.TopEntity
import com.alien.ward.utils.Converter

@Database(entities = [TopEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class MyDatabase: RoomDatabase() {
    abstract fun myDao(): MyDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MyDatabase::class.java, "top_database"
            ).build()
    }


}