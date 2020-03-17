package com.raydevelopers.trendinggitrepos.ui.fragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.raydevelopers.trendinggitrepos.R
import com.raydevelopers.trendinggitrepos.adapter.RepoListRecyclerAdapter
import com.raydevelopers.trendinggitrepos.ui.activity.TrendingRepoActivity
import com.raydevelopers.trendinggitrepos.utility.EspressoIdlingResource
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner::class)
class TrendingRepoFragmentTest{
    @get:Rule
    val activityRule = ActivityScenarioRule(TrendingRepoActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun a_test_isListFragmentVisible_onAppLaunch() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun b_test_tapListItem_isImageForItemVisible() {
        // Tap on first item
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RepoListRecyclerAdapter.ListViewHolder>(
                    1,
                    click()
                )
            )

        // Verify expanded visibility
        onView(withId(R.id.recyclerView))
            .check(matches(withViewAtPosition(1, hasDescendant(allOf(withId(R.id.text_description), isDisplayed())))))
    }

    /**
     *
     */
    private fun withViewAtPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
            }
        }
    }
}