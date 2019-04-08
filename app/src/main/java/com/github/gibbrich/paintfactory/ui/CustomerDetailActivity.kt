package com.github.gibbrich.paintfactory.ui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.github.gibbrich.paintfactory.PaintShopApp
import com.github.gibbrich.paintfactory.R
import com.github.gibbrich.paintfactory.adapter.CustomerColorsAdapter
import com.github.gibbrich.paintfactory.data.ColorsRepository
import com.github.gibbrich.paintfactory.data.CustomerRepository
import com.github.gibbrich.paintfactory.dto.CustomerDetailParams
import kotlinx.android.synthetic.main.activity_customer_detail.*
import javax.inject.Inject

class CustomerDetailActivity : AppCompatActivity() {

    companion object {
        const val CREATE_CUSTOMER_CODE = 42
        const val CHANGE_CUSTOMER_DATA = 43
        private const val TAG = "CustomerDetailActivity"
        private const val EXTRA_PARAMS = "$TAG.EXTRA_PARAMS"

        fun getIntent(context: Context, params: CustomerDetailParams) =
            Intent(context, CustomerDetailActivity::class.java).apply {
                putExtra(EXTRA_PARAMS, params)
            }
    }

    @Inject
    lateinit var customerRepository: CustomerRepository

    @Inject
    lateinit var colorsRepository: ColorsRepository

    private val params by lazy {
        intent.getParcelableExtra(EXTRA_PARAMS) as CustomerDetailParams
    }
    private lateinit var adapter: CustomerColorsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_detail)

        (application as PaintShopApp).appComponent.inject(this)

        val customerWishList = customerRepository.customers[params.customerId].wishList
        adapter = CustomerColorsAdapter(colorsRepository.colors, customerWishList)

        activity_customer_detail_colors.layoutManager = LinearLayoutManager(this)
        activity_customer_detail_colors.adapter = adapter
    }
}
