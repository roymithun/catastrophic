package com.inhouse.catastrophic.app.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.inhouse.catastrophic.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class CatDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: CatDatabase
    lateinit var dao: CatDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = database.catDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAllCats_checkIfAllValuesArePresentInDb() = runBlockingTest {
        val catEntity1 = CatEntity(1, "1", "some_url")
        val catEntity2 = CatEntity(2, "2", "some_url")
        val listOfCats = listOf<CatEntity>(catEntity1, catEntity2)

        dao.insertAll(listOfCats)

        val insertedCats: List<CatEntity> = dao.getAllCats().getOrAwaitValue()

        assertThat(insertedCats).containsExactlyElementsIn(listOfCats)
    }
}