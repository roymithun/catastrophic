package com.inhouse.catastrophic.ui.home.di

import androidx.lifecycle.ViewModelProvider
import com.inhouse.catastrophic.app.repo.CatRepository
import com.inhouse.catastrophic.ui.home.HomeFragment
import com.inhouse.catastrophic.ui.home.HomeViewModel
import dagger.Module
import dagger.Provides

@Module
class HomeModule {
    @Provides
    fun homeViewModelFactory(repositoryDefault: CatRepository): HomeViewModel.HomeViewModelFactory =
        HomeViewModel.HomeViewModelFactory(repositoryDefault)

    @Provides
    fun homeViewModel(
        homeFragment: HomeFragment,
        factory: HomeViewModel.HomeViewModelFactory
    ): HomeViewModel =
        ViewModelProvider(homeFragment, factory).get(HomeViewModel::class.java)
}