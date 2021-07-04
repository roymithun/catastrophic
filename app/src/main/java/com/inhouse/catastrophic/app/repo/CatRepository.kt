package com.inhouse.catastrophic.app.repo

import androidx.lifecycle.LiveData
import com.inhouse.catastrophic.app.utils.Resource
import com.inhouse.catastrophic.ui.data.Cat

interface CatRepository {
    suspend fun fetchAndInsertCatsIntoDb(page: Int): Resource<Void>

    fun catsList(): LiveData<List<Cat>>
}