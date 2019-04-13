package com.github.gibbrich.paintfactory.ui

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.gibbrich.paintfactory.R
import com.github.gibbrich.paintfactory.TestPaintShopApp
import com.github.gibbrich.paintfactory.adapter.CustomersAdapter
import com.github.gibbrich.paintfactory.di.AppComponentMock
import com.github.gibbrich.paintfactory.di.Injector
import com.github.gibbrich.paintfactory.domain.models.Customer
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class CustomersActivityTest {

    @Inject
    internal lateinit var customerRepository: CustomerRespository

    private val app by lazy {
        InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestPaintShopApp
    }

    @get:Rule
    val rule = ActivityTestRule(
        CustomersActivity::class.java,
        true,
        false
    )

    @get:Rule
    val intentsTestRule = IntentsTestRule(
        CustomersActivity::class.java,
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
    fun customers_screen_launch_with_empty_label() {
        rule.launchActivity(Intent())

        onView(withId(R.id.activity_customers_empty_label)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_customers_list)).check(matches(not(isDisplayed())))
        onView(withId(R.id.activity_customers_empty_label)).check(matches(withText(R.string.activity_customers_empty_label)))
    }

    @Test
    fun shows_setup_customer_and_add_color_fabs_on_main_fab_clicked() {
        rule.launchActivity(Intent())

        onView(withId(R.id.activity_customers_fab)).perform(click())
        onView(withId(R.id.activity_customers_pick_colors_button)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_customers_add_customer_button)).check(matches(isDisplayed()))
    }

    @Test
    fun customer_add_to_list_and_customer_detail_screen_open_on_add_customer_button() {
        val customers = mutableListOf<Customer>()

        whenever(customerRepository.getCustomers()).thenReturn(customers)
        whenever(customerRepository.addCustomer(any())).then {
            customers.add(Customer())
            0
        }

        intentsTestRule.launchActivity(Intent())

        onView(withId(R.id.activity_customers_fab)).perform(click())
        // only works with 2 clicks
        onView(withId(R.id.activity_customers_add_customer_button)).perform(click())
        onView(withId(R.id.activity_customers_add_customer_button)).perform(click())

        intended(hasComponent(CustomerDetailActivity::class.java.name))

        pressBack()

        onView(withId(R.id.activity_customers_empty_label)).check(matches(not(isDisplayed())))
        onView(withId(R.id.activity_customers_list)).check(matches(isDisplayed()))
    }

    @Test
    fun customer_deleted_from_list_on_swipe_to_the_left() {
        val customers = mutableListOf(Customer())

        whenever(customerRepository.getCustomers()).thenReturn(customers)
        whenever(customerRepository.deleteCustomer(0)).then {
            customers.removeAt(0)
            Unit
        }

        rule.launchActivity(Intent())

        onView(withId(R.id.activity_customers_list)).perform(RecyclerViewActions.actionOnItemAtPosition<CustomersAdapter.Holder>(0, swipeLeft()))

        onView(withId(R.id.activity_customers_empty_label)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_customers_list)).check(matches(not(isDisplayed())))
    }

    @Test
    fun customer_detail_screen_open_on_customer_click() {
        val customers = mutableListOf(Customer())

        whenever(customerRepository.getCustomers()).thenReturn(customers)

        intentsTestRule.launchActivity(Intent())

        onView(withId(R.id.activity_customers_list)).perform(RecyclerViewActions.actionOnItemAtPosition<CustomersAdapter.Holder>(0, click()))

        intended(hasComponent(CustomerDetailActivity::class.java.name))
    }
}