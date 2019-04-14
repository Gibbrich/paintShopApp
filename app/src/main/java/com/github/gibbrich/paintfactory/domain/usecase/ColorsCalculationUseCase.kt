package com.github.gibbrich.paintfactory.domain.usecase

import com.github.gibbrich.paintfactory.domain.models.ColorType
import com.github.gibbrich.paintfactory.domain.models.ColorWithType
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespository
import optimized.businessLogic.OptimizedSolver
import optimized.models.Batches
import optimized.models.OptimizedCase
import optimized.models.OptimizedCustomer

class ColorsCalculationUseCase(
    private val colorsRepository: ColorsRepository,
    private val customersRepository: CustomerRespository
) {

    /**
     * Converts selected colors and customers wishlists to list of
     * colors with types by applying algorithm of selecting colors from
     * paintShopLib.
     *
     * @return colors with types in the same order, as colors were added;
     * if there is no solution - empty list.
     */
    fun getColorsWithType(): List<ColorWithType> {
        val customers = getOptimizedCustomers()
        val case = OptimizedCase(colorsRepository.getColors().size, customers)
        val batches = OptimizedSolver.process(case)

        return getColorsWithType(batches)
    }

    private fun getColorsWithType(batches: Batches?): MutableList<ColorWithType> {
        return batches?.let {
            colorsRepository.getColors().mapIndexed { index, color ->
                val colorType = if (it.getColorType(index) == optimized.models.ColorType.GLOSSY) {
                    ColorType.GLOSSY
                } else {
                    ColorType.MATTE
                }

                ColorWithType(color.value, colorType)
            }.toMutableList()
        } ?: mutableListOf()
    }

    private fun getOptimizedCustomers(): List<OptimizedCustomer> =
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
            OptimizedCustomer(matteId, glossyWishList)
        }
}