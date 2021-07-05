package com.inhouse.catastrophic.ui

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.SmallTest
import com.inhouse.catastrophic.R
import com.inhouse.catastrophic.launchFragmentInHiltContainer
import com.inhouse.catastrophic.ui.data.Cat
import com.inhouse.catastrophic.ui.detail.DetailFragment
import com.inhouse.catastrophic.ui.home.HomeFragment
import com.inhouse.catastrophic.ui.home.adapter.PhotoAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class MainTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)
    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @Test
    fun showHomeFragment_simulateDelayForNetworkDownload_checkRecyclerView_performClickOnAnItem() {
        launchHomeFragment()

        onView(withId(R.id.rv_cat_photos))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        runBlocking { delay(1000) }
        onView(withId(R.id.rv_cat_photos))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<PhotoAdapter.PhotoViewHolder>(
                    1,
                    click()
                )
            )
    }

    @Test
    fun passCatAsArgument_loadDetailScreen_DisplaysImageView_inHiltContainer() {
        launchFragmentInHiltContainer<DetailFragment>(fragmentArgs = Bundle().apply {
            putParcelable("selectedCat", Cat("123", "https://cdn2.thecatapi.com/images/7ad.png"))
        })
        // wait for some time to allow download of image to be completed
        runBlocking { delay(1000) }
        onView(withId(R.id.iv_photo)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    private fun launchHomeFragment() {
        launchFragmentInHiltContainer<HomeFragment> {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.homeFragment)
            this.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    // The fragment’s view has just been created
                    Navigation.setViewNavController(this.requireView(), navController)
                }
            }
        }
    }
}