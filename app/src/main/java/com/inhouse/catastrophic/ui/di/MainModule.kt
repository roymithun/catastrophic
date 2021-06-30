package com.inhouse.catastrophic.ui.di

import androidx.lifecycle.ViewModelProvider
import com.inhouse.catastrophic.ui.MainActivity
import com.inhouse.catastrophic.ui.MainViewModel
import com.inhouse.catastrophic.ui.data.CatsApi
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    fun mainViewModelFactory(catsApi: CatsApi): MainViewModel.MainViewModelFactory =
        MainViewModel.MainViewModelFactory(catsApi)

    @Provides
    fun mainViewModel(
        mainActivity: MainActivity,
        factory: MainViewModel.MainViewModelFactory
    ): MainViewModel =
        ViewModelProvider(mainActivity, factory).get(MainViewModel::class.java)
}