package com.inhouse.catastrophic.app.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_items")
data class CatEntity(
    @PrimaryKey
    val _id: Int? = null,
    @ColumnInfo(name = "id") val catId: String,
    val url: String
)