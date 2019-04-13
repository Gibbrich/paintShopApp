package com.github.gibbrich.paintfactory.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.github.gibbrich.paintfactory.R
import com.github.gibbrich.paintfactory.adapter.CustomerColorsAdapter
import com.github.gibbrich.paintfactory.dto.CustomerDetailParams
import com.github.gibbrich.paintfactory.ui.viewModels.CustomerDetailModelFactory
import com.github.gibbrich.paintfactory.ui.viewModels.CustomerDetailViewModel
import kotlinx.android.synthetic.main.activity_customer_detail.*

class CustomerDetailActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "CustomerDetailActivity"
        const val EXTRA_PARAMS = "$TAG.EXTRA_PARAMS"

        fun getIntent(context: Context, params: CustomerDetailParams) =
            Intent(context, CustomerDetailActivity::class.java).apply {
                putExtra(EXTRA_PARAMS, params)
            }
    }

    private val params by lazy {
        intent.getParcelableExtra(EXTRA_PARAMS) as CustomerDetailParams
    }
    private lateinit var adapter: CustomerColorsAdapter
    private lateinit var model: CustomerDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_detail)

        model = ViewModelProviders.of(this, CustomerDetailModelFactory(params.customerId))
            .get(CustomerDetailViewModel::class.java)

        setupColorsList()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupColorsList() {
        adapter = CustomerColorsAdapter(
            model.getColors(),
            model.getCustomerWishlist(),
            model::onAddToWishListCkeckboxClicked,
            model::onIsMatteCheckboxClicked
        )
        activity_customer_detail_colors.layoutManager = LinearLayoutManager(this)
        activity_customer_detail_colors.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
