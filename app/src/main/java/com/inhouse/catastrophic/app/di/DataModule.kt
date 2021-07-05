package com.inhouse.catastrophic.app.di

import android.content.Context
import androidx.room.Room
import com.inhouse.catastrophic.app.db.CatDatabase
import com.inhouse.catastrophic.app.utils.BASE_URL
import com.inhouse.catastrophic.app.utils.DATABASE_NAME
import com.inhouse.catastrophic.ui.data.CatsApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun catDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CatDatabase::class.java, DATABASE_NAME).build()
}