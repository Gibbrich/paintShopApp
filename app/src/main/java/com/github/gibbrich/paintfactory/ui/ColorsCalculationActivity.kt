package com.github.gibbrich.paintfactory.ui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.github.gibbrich.paintfactory.R
import com.github.gibbrich.paintfactory.adapter.ColorsWithTypeAdapter
import kotlinx.android.synthetic.main.activity_colors_calculation.*

class ColorsCalculationActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context) = Intent(context, ColorsCalculationActivity::class.java)
    }

    private lateinit var adapter: ColorsWithTypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colors_calculation)

        adapter = ColorsWithTypeAdapter()
        activity_colors_calculations_list.layoutManager = LinearLayoutManager(this)
        activity_colors_calculations_list.adapter = adapter

        if (adapter.itemCount == 0) {
            cant_satisfy_customers_label.visibility = View.VISIBLE
            activity_colors_calculations_list.visibility = View.GONE
        } else {
            cant_satisfy_customers_label.visibility = View.GONE
            activity_colors_calculations_list.visibility = View.VISIBLE
        }
    }
}
