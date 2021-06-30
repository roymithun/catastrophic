package com.inhouse.catastrophic.app.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CatEntity(@PrimaryKey val id: String, val url: String)