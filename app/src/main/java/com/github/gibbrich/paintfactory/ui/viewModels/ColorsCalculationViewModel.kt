package com.github.gibbrich.paintfactory.ui.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.github.gibbrich.paintFactory.optimized.Batches
import com.github.gibbrich.paintFactory.optimized.Case
import com.github.gibbrich.paintFactory.optimized.Customer
import com.github.gibbrich.paintFactory.optimized.process
import com.github.gibbrich.paintfactory.PaintShopApp
import com.github.gibbrich.paintfactory.domain.models.ColorType
import com.github.gibbrich.paintfactory.domain.models.ColorWithType
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespoitory
import javax.inject.Inject

class ColorsCalculationViewModel(app: Application) : AndroidViewModel(app) {

    @Inject
    internal lateinit var colorsRepository: ColorsRepository

    @Inject
    internal lateinit var customersRepository: CustomerRespoitory

    init {
        getApplication<PaintShopApp>().appComponent.inject(this)
    }

    fun getColorsWithType(): List<ColorWithType> {
        val customers = getLibCustomers()
        val case = Case(colorsRepository.getColors().size, customers)
        val batches = process(case)

        return getColorsWithType(batches)
    }

    private fun getColorsWithType(batches: Batches?): MutableList<ColorWithType> {
        return batches?.let {
            colorsRepository.getColors().mapIndexed { index, color ->
                val colorType = if (it.get(index) == 0) {
                    ColorType.GLOSSY
                } else {
                    ColorType.MATTE
                }

                ColorWithType(color.value, colorType)
            }.toMutableList()
        } ?: mutableListOf()
    }

    private fun getLibCustomers(): List<Customer> =
        customersRepository.getCustomers().map {
            var matteId: Int? = null
            val glossyWishList = HashSet<Int>()
            for (entry in it.wishList) {
                val id = colorsRepository.getColorId(entry.key)
                if (entry.value == ColorType.MATTE) {
                    matteId = id
                } else {
                    glossyWishList.add(id)
                }
            }
            Customer(matteId, glossyWishList)
        }
}