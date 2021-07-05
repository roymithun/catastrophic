package com.inhouse.catastrophic.ui.home.di

import com.inhouse.catastrophic.ui.home.HomeFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance homeFragment: HomeFragment): HomeComponent
    }

    fun inject(homeFragment: HomeFragment)
}