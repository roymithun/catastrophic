package com.inhouse.catastrophic.app.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [CatEntity::class],
    exportSchema = false
)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
}