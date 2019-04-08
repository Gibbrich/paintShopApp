package com.github.gibbrich.paintfactory.ui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.github.gibbrich.paintFactory.optimized.Case
import com.github.gibbrich.paintFactory.optimized.Customer
import com.github.gibbrich.paintFactory.optimized.process
import com.github.gibbrich.paintfactory.PaintShopApp
import com.github.gibbrich.paintfactory.R
import com.github.gibbrich.paintfactory.adapter.ColorsWithTypeAdapter
import com.github.gibbrich.paintfactory.data.ColorsRepository
import com.github.gibbrich.paintfactory.data.CustomerRepository
import com.github.gibbrich.paintfactory.domain.ColorType
import com.github.gibbrich.paintfactory.domain.ColorWithType
import kotlinx.android.synthetic.main.activity_colors_calculation.*
import javax.inject.Inject

class ColorsCalculationActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context) = Intent(context, ColorsCalculationActivity::class.java)
    }

    @Inject
    internal lateinit var colorsRepository: ColorsRepository

    @Inject
    internal lateinit var customersRepository: CustomerRepository

    private lateinit var adapter: ColorsWithTypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colors_calculation)

        (application as PaintShopApp).appComponent.inject(this)

        val customers = customersRepository.customers.map {
            var matteId: Int? = null
            val glossyWishList = HashSet<Int>()
            it.wishList.forEach { entry ->
                val id = colorsRepository.colors.indexOf(entry.key)
                if (entry.value == ColorType.MATTE) {
                    matteId = id
                } else {

                }
            }
            Customer(matteId, glossyWishList)
        }

        val case = Case(colorsRepository.colors.size, customers)
        val batches = process(case)

        val list = batches?.let {
            colorsRepository.colors.mapIndexed { index, color ->
                val colorType = if (it.get(index) == 0) {
                    ColorType.GLOSSY
                } else {
                    ColorType.MATTE
                }

                ColorWithType(color.value, colorType)
            }.toMutableList()
        } ?: mutableListOf()

        adapter = ColorsWithTypeAdapter(list)
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
