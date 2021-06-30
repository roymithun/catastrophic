package com.inhouse.catastrophic.app

import android.app.Application
import com.inhouse.catastrophic.app.di.AppComponent
import com.inhouse.catastrophic.app.di.DaggerAppComponent

class BaseApplication : Application() {
    val appComponent: AppComponent by lazy { DaggerAppComponent.factory().create(this) }
}