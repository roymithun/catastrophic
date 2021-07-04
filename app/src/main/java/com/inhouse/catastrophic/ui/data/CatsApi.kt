package com.inhouse.catastrophic.ui.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsApi {
    @GET("search")
    suspend fun getCats(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("mime_types") mimeTypes: String
    ): Response<List<Cat>>
}