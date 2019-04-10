package com.github.gibbrich.paintfactory.ui

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.github.gibbrich.paintfactory.PaintShopApp
import com.github.gibbrich.paintfactory.R
import com.github.gibbrich.paintfactory.adapter.ColorsWithTypeAdapter
import com.github.gibbrich.paintfactory.di.AppComponentMock
import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.models.ColorType
import com.github.gibbrich.paintfactory.domain.models.Customer
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespoitory
import com.github.gibbrich.paintfactory.utils.withRecyclerView
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertTrue
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class ColorsCalculationActivityTest {
    @Inject
    lateinit var customerRepository: CustomerRespoitory

    @Inject
    lateinit var colorsRepository: ColorsRepository

    @get:Rule
    val rule = ActivityTestRule(
        ColorsCalculationActivity::class.java,
        true,
        false
    )

    @Before
    fun setUp() {
        val app = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as PaintShopApp

        (app.appComponent as AppComponentMock).inject(this)
    }

    @Test
    fun colors_calculation_screen_opens_with_empty_view_if_no_colors_was_added() {
        whenever(colorsRepository.getColors()).thenReturn(emptyList())
        whenever(customerRepository.getCustomers()).thenReturn(emptyList())

        rule.launchActivity(Intent())

        onView(withId(R.id.cant_satisfy_customers_label)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_colors_calculations_list)).check(matches(not(isDisplayed())))
    }

    @Test
    fun colors_calculation_screen_opens_with_colors_calculation_result() {
        val red = Color(-0x10000)
        val blue = Color(-0xCBE801)

        val colors = listOf(red, blue)
        whenever(colorsRepository.getColors()).thenReturn(colors)

        val wishlist = mutableMapOf(red to ColorType.MATTE)
        val customer = Customer(wishlist)
        whenever(customerRepository.getCustomers()).thenReturn(listOf(customer))

        whenever(colorsRepository.getColorId(red)).thenReturn(0)
        whenever(colorsRepository.getColorId(blue)).thenReturn(1)

        val activity = rule.launchActivity(Intent())

        onView(withId(R.id.cant_satisfy_customers_label)).check(matches(not(isDisplayed())))
        onView(withId(R.id.activity_colors_calculations_list)).check(matches(isDisplayed()))

        val adapter = activity.findViewById<RecyclerView>(R.id.activity_colors_calculations_list).adapter as ColorsWithTypeAdapter

        assertTrue(adapter.itemCount == 2)

        onView(withRecyclerView(R.id.activity_colors_calculations_list)
            .atPositionOnView(0, R.id.color_with_types_matte_checkbox))
            .check(matches(isChecked()))

        onView(withRecyclerView(R.id.activity_colors_calculations_list)
            .atPositionOnView(1, R.id.color_with_types_matte_checkbox))
            .check(matches(isNotChecked()))

    }
}