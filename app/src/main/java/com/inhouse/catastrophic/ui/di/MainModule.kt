package com.inhouse.catastrophic.ui.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.inhouse.catastrophic.app.db.CatDatabase
import com.inhouse.catastrophic.app.di.ActivityScope
import com.inhouse.catastrophic.app.repo.CatRepository
import com.inhouse.catastrophic.app.repo.DefaultCatRepository
import com.inhouse.catastrophic.app.utils.DATABASE_NAME
import com.inhouse.catastrophic.ui.MainActivity
import com.inhouse.catastrophic.ui.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    fun mainViewModelFactory(repositoryDefault: CatRepository): MainViewModel.MainViewModelFactory =
        MainViewModel.MainViewModelFactory(repositoryDefault)

    @Provides
    fun mainViewModel(
        mainActivity: MainActivity,
        factory: MainViewModel.MainViewModelFactory
    ): MainViewModel =
        ViewModelProvider(mainActivity, factory).get(MainViewModel::class.java)
}