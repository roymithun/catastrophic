package com.inhouse.catastrophic.app.di

import com.inhouse.catastrophic.ui.di.MainComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppSubComponents::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }

    fun mainComponent(): MainComponent.Factory
}