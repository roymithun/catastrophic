package com.inhouse.catastrophic.app

import android.app.Application
import com.inhouse.catastrophic.app.di.AppComponent
import com.inhouse.catastrophic.app.di.DaggerAppComponent

open class BaseApplication : Application() {
    val appComponent: AppComponent by lazy { initializeComponent() }

    open fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent.factory().create(applicationContext)
    }
}