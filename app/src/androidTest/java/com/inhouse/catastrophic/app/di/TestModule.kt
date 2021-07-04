package com.inhouse.catastrophic.app.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.inhouse.catastrophic.app.db.CatDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class TestModule {
    @Provides
    fun context(): Context = ApplicationProvider.getApplicationContext()

    @Provides
    @Named("test_db")
    fun catDatabase(context: Context): CatDatabase = Room.inMemoryDatabaseBuilder(
        context,
        CatDatabase::class.java
    ).allowMainThreadQueries().build()
}