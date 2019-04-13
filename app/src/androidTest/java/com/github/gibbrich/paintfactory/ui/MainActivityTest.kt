package com.github.gibbrich.paintfactory.ui

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.RootMatchers.isDialog
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.gibbrich.paintfactory.R
import com.github.gibbrich.paintfactory.TestPaintShopApp
import com.github.gibbrich.paintfactory.adapter.ColorsAdapter
import com.github.gibbrich.paintfactory.di.AppComponentMock
import com.github.gibbrich.paintfactory.di.Injector
import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespository
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Inject
    internal lateinit var colorsRepository: ColorsRepository

    @Inject
    internal lateinit var customerRespoitory: CustomerRespository

    private val app by lazy {
        InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestPaintShopApp
    }

    @get:Rule
    val rule = ActivityTestRule(
        MainActivity::class.java,
        true,
        false
    )

    @get:Rule
    val intentsTestRule = IntentsTestRule(
        MainActivity::class.java,
        true,
        false
    )

    @Before
    fun setUp() {
        val component = app.createComponent() as AppComponentMock
        Injector.init(component)
        component.inject(this)
    }

    @Test
    fun colors_screen_launch_with_empty_label() {
        rule.launchActivity(Intent())

        onView(withId(R.id.activity_main_empty_label)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_main_colors_list)).check(matches(not(isDisplayed())))
        onView(withId(R.id.activity_main_empty_label)).check(matches(withText(R.string.activity_main_empty_label)))
    }

    @Test
    fun shows_setup_customer_and_add_color_fabs_on_main_fab_clicked() {
        rule.launchActivity(Intent())

        onView(withId(R.id.activity_main_fab)).perform(click())
        onView(withId(R.id.activity_main_setup_customers)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_main_choose_color_button)).check(matches(isDisplayed()))
    }

    @Test
    fun color_chooser_opens_on_choose_color_button_click() {
        rule.launchActivity(Intent())

        onView(withId(R.id.activity_main_fab)).perform(click())

        // only works with 2 clicks
        onView(withId(R.id.activity_main_choose_color_button)).perform(click())
        onView(withId(R.id.activity_main_choose_color_button)).perform(click())

        onView(withText(R.string.activity_main_choose_color))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    @Test
    fun color_added_to_list_on_color_chooser_ok_clicked() {
        val mockkColors = mutableListOf<Color>()

        whenever(colorsRepository.addColor(Color(2131297324))).then {
            mockkColors.add(Color(2131297324))
            0
        }

        whenever(colorsRepository.getColors()).thenReturn(mockkColors)

        rule.launchActivity(Intent())

        onView(withId(R.id.activity_main_fab)).perform(click())
        // only works with 2 clicks
        onView(withId(R.id.activity_main_choose_color_button)).perform(click())
        onView(withId(R.id.activity_main_choose_color_button)).perform(click())
        onView(withText(R.string.activity_main_ok))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.activity_main_empty_label)).check(matches(not(isDisplayed())))
        onView(withId(R.id.activity_main_colors_list)).check(matches(isDisplayed()))
    }

    @Test
    fun color_deleted_from_list_on_swipe_to_the_left() {
        val color = Color(2131297324)
        val colors = mutableListOf(color)
        whenever(colorsRepository.getColors()).thenReturn(colors)

        whenever(colorsRepository.removeColor(0)).then {
            colors.removeAt(0)
            Unit
        }

        rule.launchActivity(Intent())

        onView(withId(R.id.activity_main_colors_list)).perform(RecyclerViewActions.actionOnItemAtPosition<ColorsAdapter.Holder>(0, swipeLeft()))

        onView(withId(R.id.activity_main_empty_label)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_main_colors_list)).check(matches(not(isDisplayed())))
    }

    @Test
    fun customers_screen_open_on_setup_customers_button_click() {
        intentsTestRule.launchActivity(Intent())

        onView(withId(R.id.activity_main_fab)).perform(click())
        // only works with 2 clicks
        onView(withId(R.id.activity_main_setup_customers)).perform(click())
        onView(withId(R.id.activity_main_setup_customers)).perform(click())

        intended(hasComponent(CustomersActivity::class.java.name))
    }
}