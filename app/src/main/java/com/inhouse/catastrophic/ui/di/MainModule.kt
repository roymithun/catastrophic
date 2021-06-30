package com.inhouse.catastrophic.ui.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.inhouse.catastrophic.app.db.CatDatabase
import com.inhouse.catastrophic.app.repo.CatRepository
import com.inhouse.catastrophic.ui.MainActivity
import com.inhouse.catastrophic.ui.MainViewModel
import com.inhouse.catastrophic.ui.data.CatsApi
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    fun catDatabase(context: Context) = CatDatabase.getInstance(context)

    @Provides
    fun catRepository(catDatabase: CatDatabase, catsApi: CatsApi): CatRepository =
        CatRepository(catDatabase, catsApi)

    @Provides
    fun mainViewModelFactory(repository: CatRepository): MainViewModel.MainViewModelFactory =
        MainViewModel.MainViewModelFactory(repository)

    @Provides
    fun mainViewModel(
        mainActivity: MainActivity,
        factory: MainViewModel.MainViewModelFactory
    ): MainViewModel =
        ViewModelProvider(mainActivity, factory).get(MainViewModel::class.java)
}