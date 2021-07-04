package com.inhouse.catastrophic.app

import com.inhouse.catastrophic.app.di.AppComponent
import com.inhouse.catastrophic.app.di.DaggerTestAppComponent

class TestBaseApplication : BaseApplication() {
    override fun initializeComponent(): AppComponent {
        return DaggerTestAppComponent.create()
    }
}
