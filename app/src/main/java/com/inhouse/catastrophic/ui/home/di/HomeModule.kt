package com.inhouse.catastrophic.ui.home.di

import com.inhouse.catastrophic.app.repo.CatRepository
import com.inhouse.catastrophic.app.repo.DefaultCatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeModule {
    @ViewModelScoped
    @Binds
    abstract fun bindCatRepository(repository: DefaultCatRepository): CatRepository
}