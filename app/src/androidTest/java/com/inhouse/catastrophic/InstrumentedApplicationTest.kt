package com.inhouse.catastrophic

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.inhouse.catastrophic.ui.MainActivity
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class InstrumentedApplicationTest {
//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.inhouse.catastrophic", appContext.packageName)
//    }

    @Test
    fun runApp() {
        ActivityScenario.launch(MainActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.rv_cat_photos))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}