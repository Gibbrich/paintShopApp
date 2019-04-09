package com.github.gibbrich.paintfactory.ui.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.github.gibbrich.paintFactory.optimized.Case
import com.github.gibbrich.paintFactory.optimized.Customer
import com.github.gibbrich.paintFactory.optimized.process
import com.github.gibbrich.paintfactory.PaintShopApp
import com.github.gibbrich.paintfactory.data.ColorsRepository
import com.github.gibbrich.paintfactory.data.CustomerRepository
import com.github.gibbrich.paintfactory.domain.ColorType
import com.github.gibbrich.paintfactory.domain.ColorWithType
import javax.inject.Inject

class ColorsCalculationViewModel(app: Application) : AndroidViewModel(app) {

    @Inject
    internal lateinit var colorsRepository: ColorsRepository

    @Inject
    internal lateinit var customersRepository: CustomerRepository

    init {
        getApplication<PaintShopApp>().appComponent.inject(this)
    }

    fun getColorsWithType(): MutableList<ColorWithType> {
        val customers = customersRepository.customers.map {
            var matteId: Int? = null
            val glossyWishList = HashSet<Int>()
            it.wishList.forEach { entry ->
                val id = colorsRepository.colors.indexOf(entry.key)
                if (entry.value == ColorType.MATTE) {
                    matteId = id
                } else {
                    glossyWishList.add(id)
                }
            }
            Customer(matteId, glossyWishList)
        }

        val case = Case(colorsRepository.colors.size, customers)
        val batches = process(case)

        return batches?.let {
            colorsRepository.colors.mapIndexed { index, color ->
                val colorType = if (it.get(index) == 0) {
                    ColorType.GLOSSY
                } else {
                    ColorType.MATTE
                }

                ColorWithType(color.value, colorType)
            }.toMutableList()
        } ?: mutableListOf()
    }
}