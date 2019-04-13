package com.github.gibbrich.paintfactory.ui

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.github.gibbrich.paintfactory.R
import com.github.gibbrich.paintfactory.TestPaintShopApp
import com.github.gibbrich.paintfactory.adapter.CustomerColorsAdapter
import com.github.gibbrich.paintfactory.di.AppComponentMock
import com.github.gibbrich.paintfactory.di.Injector
import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.models.ColorType
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespository
import com.github.gibbrich.paintfactory.dto.CustomerDetailParams
import com.github.gibbrich.paintfactory.utils.withRecyclerView
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class CustomerDetailActivityTest {

    @Inject
    lateinit var customerRepository: CustomerRespository

    @Inject
    lateinit var colorsRepository: ColorsRepository

    private val intent = Intent().apply {
        putExtra(CustomerDetailActivity.EXTRA_PARAMS, CustomerDetailParams(0))
    }

    private val app by lazy {
        InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as TestPaintShopApp
    }

    @get:Rule
    val rule = ActivityTestRule(
        CustomerDetailActivity::class.java,
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
    fun customer_detail_screen_starts_with_empty_list_if_there_is_no_color_selected() {
        whenever(colorsRepository.getColors()).thenReturn(emptyList())
        whenever(customerRepository.getCustomerWishList(0)).thenReturn(mutableMapOf())

        val activity = rule.launchActivity(intent)

        val itemCount = activity.findViewById<RecyclerView>(R.id.activity_customer_detail_colors).adapter!!.itemCount

        assertTrue(itemCount == 0)
    }

    @Test
    fun customer_detail_screen_starts_with_no_items_checked() {
        val colors = listOf(
            Color(-0xCBE801),
            Color(-0x10000)
        )
        whenever(colorsRepository.getColors()).thenReturn(colors)
        whenever(customerRepository.getCustomerWishList(0)).thenReturn(mutableMapOf())

        val activity = rule.launchActivity(intent)

        val adapter = activity.findViewById<RecyclerView>(R.id.activity_customer_detail_colors).adapter as CustomerColorsAdapter

        assertTrue(adapter.itemCount == 2)

        for (i in 0..1) {
            onView(withRecyclerView(R.id.activity_customer_detail_colors)
                .atPositionOnView(i, R.id.is_in_wishlist_check_box))
                .check(matches(isNotChecked()))

            onView(withRecyclerView(R.id.activity_customer_detail_colors)
                .atPositionOnView(i, R.id.is_matte_check_box))
                .check(matches(isNotChecked()))
        }
    }

    @Test
    fun isInWishList_checkbox_checked_on_isMatte_checkbox_checked() {
        val colors = listOf(
            Color(-0xCBE801),
            Color(-0x10000)
        )
        whenever(colorsRepository.getColors()).thenReturn(colors)
        whenever(customerRepository.getCustomerWishList(0)).thenReturn(mutableMapOf())

        rule.launchActivity(intent)

        onView(withRecyclerView(R.id.activity_customer_detail_colors)
            .atPositionOnView(0, R.id.is_matte_check_box))
            .perform(click())

        onView(withRecyclerView(R.id.activity_customer_detail_colors)
            .atPositionOnView(0, R.id.is_in_wishlist_check_box))
            .check(matches(isChecked()))
    }

    @Test
    fun isMatte_checkbox_unchecked_on_isInWishList_checkbox_unchecked() {
        val red = Color(-0x10000)
        val blue = Color(-0xCBE801)

        val colors = listOf(red, blue)
        whenever(colorsRepository.getColors()).thenReturn(colors)

        val wishlist = mutableMapOf(red to ColorType.MATTE)
        whenever(customerRepository.getCustomerWishList(0)).thenReturn(wishlist)

        rule.launchActivity(intent)

        onView(withRecyclerView(R.id.activity_customer_detail_colors)
            .atPositionOnView(0, R.id.is_in_wishlist_check_box))
            .perform(click())

        onView(withRecyclerView(R.id.activity_customer_detail_colors)
            .atPositionOnView(0, R.id.is_matte_check_box))
            .check(matches(isNotChecked()))
    }

    @Test
    fun isMatte_checkbox_unchecked_on_other_item_isMatte_checkbox_checked() {
        val red = Color(-0x10000)
        val blue = Color(-0xCBE801)

        val colors = listOf(red, blue)
        whenever(colorsRepository.getColors()).thenReturn(colors)

        val wishlist = mutableMapOf(red to ColorType.MATTE)
        whenever(customerRepository.getCustomerWishList(0)).thenReturn(wishlist)

        rule.launchActivity(intent)

        onView(withRecyclerView(R.id.activity_customer_detail_colors)
            .atPositionOnView(1, R.id.is_matte_check_box))
            .perform(click())

        onView(withRecyclerView(R.id.activity_customer_detail_colors)
            .atPositionOnView(0, R.id.is_matte_check_box))
            .check(matches(isNotChecked()))
    }
}