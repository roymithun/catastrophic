package com.inhouse.catastrophic.ui.di

import com.inhouse.catastrophic.ui.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [MainModule::class])
interface MainComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance activity: MainActivity): MainComponent
    }

    fun inject(activity: MainActivity)
}