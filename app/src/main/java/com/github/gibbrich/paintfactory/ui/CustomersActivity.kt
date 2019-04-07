package com.github.gibbrich.paintfactory.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.github.gibbrich.paintfactory.domain.Customer
import com.github.gibbrich.paintfactory.PaintShopApp
import com.github.gibbrich.paintfactory.R
import com.github.gibbrich.paintfactory.adapter.CustomersAdapter
import com.github.gibbrich.paintfactory.data.CustomerRepository
import com.github.gibbrich.paintfactory.dto.CustomerDetailParams
import kotlinx.android.synthetic.main.activity_customers.*
import javax.inject.Inject

class CustomersActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: CustomerRepository

    private lateinit var adapter: CustomersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customers)

        (application as PaintShopApp).appComponent.inject(this)

        adapter = CustomersAdapter(this::onCustomerClicked)
        activity_customers_list.layoutManager = LinearLayoutManager(this)

        activity_customers_add_button.setOnClickListener {
            repository.customers.add(Customer())
            val params = CustomerDetailParams(repository.customers.lastIndex)
            val intent = CustomerDetailActivity.getIntent(this, params)
            startActivityForResult(intent, CustomerDetailActivity.CREATE_CUSTOMER_CODE)
        }
    }

    private fun onCustomerClicked(customerId: Int) {
        val params = CustomerDetailParams(customerId)
        val intent = CustomerDetailActivity.getIntent(this, params)
        startActivityForResult(intent, CustomerDetailActivity.CHANGE_CUSTOMER_DATA)
    }
}
