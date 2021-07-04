package com.inhouse.catastrophic.app.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.inhouse.catastrophic.app.TestBaseApplication
import com.inhouse.catastrophic.app.di.TestAppComponent
import com.inhouse.catastrophic.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CatDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val appComponent: TestAppComponent =
        (ApplicationProvider.getApplicationContext() as TestBaseApplication)
            .appComponent as TestAppComponent


    @Inject
    @Named("test_db")
    lateinit var database: CatDatabase
    lateinit var dao: CatDao

    @Before
    fun setUp() {
        appComponent.inject(this)
        dao = database.catDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAllCats_checkIfAllValuesArePresentInDb() = runBlockingTest {
        val catEntity1 = CatEntity("1", "some_url")
        val catEntity2 = CatEntity("2", "some_url")
        val listOfCats = listOf<CatEntity>(catEntity1, catEntity2)

        dao.insertAll(listOfCats)

        val insertedCats: List<CatEntity> = dao.getAllCats().getOrAwaitValue()

        assertThat(insertedCats).containsExactlyElementsIn(listOfCats)
    }
}