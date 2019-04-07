package com.github.gibbrich.paintfactory.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.github.gibbrich.paintfactory.domain.Customer
import com.github.gibbrich.paintfactory.R
import kotlinx.android.synthetic.main.customer_item_layout.view.*

class CustomersAdapter(
    private val onItemClicked: (Int) -> Unit,
    items: MutableList<Customer> = mutableListOf()
): ConstantValueAdapter<Customer, CustomersAdapter.Holder>(items) {
    override fun createHolder(view: View): Holder = Holder(
        customerTitleLabel = view.customer_item_title_label,
        view = view
    )

    override val lineResourceId = R.layout.customer_item_layout

    override fun bind(holder: Holder, item: Customer, position: Int) {
        holder.customerTitleLabel.text = "Customer#${position + 1}"
        holder.itemView.setOnClickListener {
            onItemClicked.invoke(position)
        }
    }

    class Holder(
        val customerTitleLabel: TextView,
        view: View
    ): RecyclerView.ViewHolder(view)
}