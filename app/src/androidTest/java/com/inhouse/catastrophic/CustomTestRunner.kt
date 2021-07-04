package com.inhouse.catastrophic

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.inhouse.catastrophic.app.TestBaseApplication

class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestBaseApplication::class.java.name, context)
    }
}