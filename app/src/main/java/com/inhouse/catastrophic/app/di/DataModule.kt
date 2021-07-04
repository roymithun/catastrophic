package com.inhouse.catastrophic.app.di

import android.content.Context
import androidx.room.Room
import com.inhouse.catastrophic.app.db.CatDatabase
import com.inhouse.catastrophic.app.repo.CatRepository
import com.inhouse.catastrophic.app.repo.DefaultCatRepository
import com.inhouse.catastrophic.app.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun catDatabase(context: Context) =
        Room.databaseBuilder(context, CatDatabase::class.java, DATABASE_NAME).build()

    @Provides
    fun catRepository(catDatabase: CatDatabase): CatRepository =
        DefaultCatRepository(catDatabase)
}