package com.jrodriguezva.superhero.ui


import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.jrodriguezva.superhero.R
import com.jrodriguezva.superhero.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@LargeTest
class SuperheroDetailActivityTest {


    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SuperheroDetailActivity::class.java,true,true)

    /**
     * Prepare your test fixture for this test. In this case we register an IdlingResources with
     * Espresso. IdlingResource resource is a great way to tell Espresso when your app is in an
     * idle state. This helps Espresso to synchronize your test actions, which makes tests
     * significantly more reliable.
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun mainActivityTest3() {

        Espresso.onView(ViewMatchers.withId(R.id.superheroRealName))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(TextView::class.java)))

        Espresso.onView(ViewMatchers.withId(R.id.superheroHeight))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(TextView::class.java)))

        Espresso.onView(ViewMatchers.withId(R.id.superheroAbilities))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(TextView::class.java)))

        Espresso.onView(ViewMatchers.withId(R.id.superheroPower))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(TextView::class.java)))

        Espresso.onView(ViewMatchers.withId(R.id.superheroGroup))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(TextView::class.java)))
    }
}
