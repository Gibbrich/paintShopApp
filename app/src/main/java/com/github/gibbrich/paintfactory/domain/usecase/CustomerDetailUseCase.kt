package com.github.gibbrich.paintfactory.domain.usecase

import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.models.ColorType
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespository

class CustomerDetailUseCase(
    val customerRepository: CustomerRespository,
    val colorsRepository: ColorsRepository
) {
    fun changeMatteColors(
        customerId: Int,
        isChecked: Boolean,
        item: Color
    ) {
        val wishList = customerRepository.getCustomerWishList(customerId)
        if (isChecked) {
            wishList.put(item, ColorType.MATTE)

            for (customerColor in wishList) {
                if (customerColor.key != item) {
                    wishList[customerColor.key] = ColorType.GLOSSY
                }
            }
        } else {
            if (item in wishList) {
                wishList.put(item, ColorType.GLOSSY)
            }
        }
    }

    fun addColorToWishlist(
        customerId: Int,
        isChecked: Boolean,
        color: Color,
        isMatteChecked: Boolean
    ) {
        val wishList = customerRepository.getCustomerWishList(customerId)
        if (isChecked) {
            val colorType = if (isMatteChecked) ColorType.MATTE else ColorType.GLOSSY
            wishList.put(color, colorType)
        } else {
            wishList.remove(color)
        }
    }

    fun getCustomerWishlist(customerId: Int): Map<Color, ColorType> = customerRepository.getCustomerWishList(customerId)

    fun getColors() = colorsRepository.getColors()
}