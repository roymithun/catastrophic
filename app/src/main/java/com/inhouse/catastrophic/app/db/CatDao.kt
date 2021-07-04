package com.inhouse.catastrophic.app.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CatDao {
    @Query("SELECT * from cat_items")
    fun getAllCats(): LiveData<List<CatEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(videos: List<CatEntity>)
}