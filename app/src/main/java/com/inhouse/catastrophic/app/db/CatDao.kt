package com.inhouse.catastrophic.app.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CatDao {
    @Query("SELECT * from catentity")
    fun getAllCats(): LiveData<List<CatEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(videos: List<CatEntity>)
}