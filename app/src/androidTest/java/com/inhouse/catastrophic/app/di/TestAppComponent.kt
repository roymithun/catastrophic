package com.inhouse.catastrophic.app.di

import com.inhouse.catastrophic.app.db.CatDaoTest
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Singleton
@Component(modules = [TestModule::class, DataModule::class])
interface TestAppComponent : AppComponent {
    @ExperimentalCoroutinesApi
    fun inject(catDaoTest: CatDaoTest)
}