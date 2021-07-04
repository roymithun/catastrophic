package com.inhouse.catastrophic.ui.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.inhouse.catastrophic.CoroutineTestRule
import com.inhouse.catastrophic.app.utils.MIME_TYPE
import com.inhouse.catastrophic.app.utils.RESPONSE_LIMIT
import com.inhouse.catastrophic.utils.MockResponseFileReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
class CatsApiTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private var mockWebServer = MockWebServer()
    private lateinit var catsApi: CatsApi

    @Before
    fun setUp() {
        mockWebServer.start()
        catsApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .build()
            .create(CatsApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `fetch cats contains item from the mock response`() {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("success_response.json").content)

        mockWebServer.enqueue(response)
        // Act
        runBlocking(coroutineTestRule.testDispatcher) {
            val actualResponse: List<Cat>? = catsApi.getCats(RESPONSE_LIMIT, 1, MIME_TYPE)
            // Assert
            assertThat(actualResponse).contains(
                Cat(
                    id = "2k3",
                    url = "https://cdn2.thecatapi.com/images/2k3.png"
                )
            )
        }
    }

    @Test
    fun `fetch cats doesn't contain item from the mock response`() {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("success_response.json").content)

        mockWebServer.enqueue(response)
        // Act
        runBlocking(coroutineTestRule.testDispatcher) {
            val actualResponse: List<Cat>? = catsApi.getCats(RESPONSE_LIMIT, 1, MIME_TYPE)
            // Assert
            assertThat(actualResponse).doesNotContain(
                Cat(
                    id = "abc",
                    url = "https://cdn2.thecatapi.com/images/2k3.png"
                )
            )
        }
    }
}