package com.github.gibbrich.paintfactory.ui

import android.content.Context
import android.content.Intent
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

    companion object {
        fun getIntent(context: Context) = Intent(context, CustomersActivity::class.java)
    }

    @Inject
    lateinit var customerRepository: CustomerRepository

    private lateinit var adapter: CustomersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customers)

        (application as PaintShopApp).appComponent.inject(this)

        adapter = CustomersAdapter(this::onCustomerClicked, customerRepository.customers)
        activity_customers_list.layoutManager = LinearLayoutManager(this)
        activity_customers_list.adapter = adapter

        activity_customers_add_button.setOnClickListener {
            val customer = Customer()
            customerRepository.customers.add(customer)
            adapter.notifyItemInserted(customerRepository.customers.lastIndex)
            val params = CustomerDetailParams(customerRepository.customers.lastIndex)
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
