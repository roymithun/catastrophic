package com.inhouse.catastrophic.app.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.inhouse.catastrophic.app.db.CatDatabase
import com.inhouse.catastrophic.app.db.CatEntity
import com.inhouse.catastrophic.app.utils.MIME_TYPE
import com.inhouse.catastrophic.app.utils.RESPONSE_LIMIT
import com.inhouse.catastrophic.app.utils.Resource
import com.inhouse.catastrophic.ui.data.Cat
import com.inhouse.catastrophic.ui.data.CatsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class DefaultCatRepository(
    private val catDatabase: CatDatabase,
    private val catsApi: CatsApi
) : CatRepository {

    override suspend fun fetchAndInsertCatsIntoDb(page: Int): Resource<Void> {
        return try {
            withContext(Dispatchers.IO) {
                Resource.loading(null)
                val catListResponse: Response<List<Cat>> =
                    catsApi.getCats(RESPONSE_LIMIT, page, MIME_TYPE)
                if (catListResponse.isSuccessful) {
                    catListResponse.body()?.let {
                        // if successful response insert into db
                        it.let {
                            catDatabase.catDao().insertAll(it.map { cat ->
                                CatEntity(
                                    cat.id,
                                    cat.url
                                )
                            })
                        }
                        return@let Resource.success(null)
                    } ?: Resource.error("Some unknown error occurred", null)
                } else {
                    Resource.error("Some unknown error occurred", null)
                }
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }

    override fun catsList(): LiveData<List<Cat>> =
        Transformations.map(catDatabase.catDao().getAllCats()) { cats ->
            cats.map { Cat(it.id, it.url) }
        }
}