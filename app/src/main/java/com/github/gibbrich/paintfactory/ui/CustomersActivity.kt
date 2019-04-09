package com.github.gibbrich.paintfactory.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.github.gibbrich.paintfactory.domain.Customer
import com.github.gibbrich.paintfactory.R
import com.github.gibbrich.paintfactory.adapter.CustomersAdapter
import com.github.gibbrich.paintfactory.adapter.SwipeToDeleteCallback
import com.github.gibbrich.paintfactory.dto.CustomerDetailParams
import com.github.gibbrich.paintfactory.ui.viewModels.CustomersActivityViewModel
import com.github.gibbrich.paintfactory.utils.ListChangeType
import kotlinx.android.synthetic.main.activity_customers.*

class CustomersActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context) = Intent(context, CustomersActivity::class.java)
    }

    private lateinit var model: CustomersActivityViewModel
    private lateinit var adapter: CustomersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customers)

        model = ViewModelProvider
            .AndroidViewModelFactory(application)
            .create(CustomersActivityViewModel::class.java)

        model.actions.observe(this, Observer { it?.let(this::handleAction) })

        setupCustomersListView()

        activity_customers_add_customer_button.setOnClickListener {
            model.onAddCustomerButtonClicked()
        }

        activity_customers_pick_colors_button.setOnClickListener {
            model.onPickColorsButtonClicked()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun handleAction(action: CustomersActivityViewModel.Action) = when (action) {
        is CustomersActivityViewModel.Action.SwitchToCustomerDetailScreen -> {
            startActivity(CustomerDetailActivity.getIntent(this, action.params))
        }

        CustomersActivityViewModel.Action.SwitchToColorCalculationScreen -> {
            startActivity(ColorsCalculationActivity.getIntent(this))
        }

        is CustomersActivityViewModel.Action.ChangeCustomerList -> {
            changeCustomerList(action.customerId, action.changeType)
        }
    }

    private fun changeCustomerList(
        customerId: Int,
        changeType: ListChangeType
    ) = when (changeType) {
        ListChangeType.ADD -> {
            adapter.notifyItemInserted(customerId)
            changeCustomersListVisibility()
        }

        ListChangeType.REMOVE -> {
            adapter.notifyItemRemoved(customerId)
            changeCustomersListVisibility()
        }
    }

    private fun changeCustomersListVisibility() {
        if (adapter.itemCount == 0) {
            activity_customers_empty_label.visibility = View.VISIBLE
            activity_customers_list.visibility = View.GONE
        } else {
            activity_customers_empty_label.visibility = View.GONE
            activity_customers_list.visibility = View.VISIBLE
        }
    }

    private fun setupCustomersListView() {
        adapter = CustomersAdapter(model::onCustomerClicked, model.getCustomers())
        activity_customers_list.layoutManager = LinearLayoutManager(this)
        activity_customers_list.adapter = adapter

        val swipeToDeleteCallback = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(holder: RecyclerView.ViewHolder, position: Int) {
                adapter.remove(holder.adapterPosition)
            }
        }

        val touchHelper = ItemTouchHelper(swipeToDeleteCallback)
        touchHelper.attachToRecyclerView(activity_customers_list)

        changeCustomersListVisibility()
    }
}
