package com.inhouse.catastrophic.app.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.inhouse.catastrophic.app.db.CatDatabase
import com.inhouse.catastrophic.app.db.CatEntity
import com.inhouse.catastrophic.ui.data.Cat
import com.inhouse.catastrophic.ui.data.CatsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CatRepository(private val catDatabase: CatDatabase, private val catsApi: CatsApi) {
    companion object {
        const val RESPONSE_LIMIT = 20
        const val MIME_TYPE = "png"
    }

    suspend fun getCats(page: Int) {
        withContext(Dispatchers.IO) {
            val catList: List<Cat>? = catsApi.getCats(RESPONSE_LIMIT, page, MIME_TYPE)
            catList?.let { cats ->
                catDatabase.catDao.insertAll(cats.map {
                    CatEntity(
                        it.id,
                        it.url
                    )
                })
            }
        }
    }

    val catsList: LiveData<List<Cat>> =
        Transformations.map(catDatabase.catDao.getAllCats()) { cats ->
            cats.map { Cat(it.id, it.url) }
        }
}