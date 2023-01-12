package com.example.mybusiness.data.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mybusiness.domain.BusinessItemDbModel

@Database(entities = [BusinessItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun businessListDao(): BusinessListDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    "business_item.db"
                )
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}