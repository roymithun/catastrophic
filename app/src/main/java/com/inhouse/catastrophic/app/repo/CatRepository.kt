package com.inhouse.catastrophic.app.repo

import androidx.lifecycle.LiveData
import com.inhouse.catastrophic.ui.data.Cat

interface CatRepository {
    suspend fun fetchAndInsertCatsIntoDb(page: Int)

    fun catsList(): LiveData<List<Cat>>
}