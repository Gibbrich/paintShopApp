package com.github.gibbrich.paintfactory.domain.usecase

import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.models.ColorType
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespository

class CustomerDetailUseCase(
    val customerRepository: CustomerRespository,
    val colorsRepository: ColorsRepository
) {
    fun updateWishlist(
        customerId: Int,
        isInWishlist: Boolean,
        color: Color,
        isMatte: Boolean
    ) {
        val wishList = customerRepository.getCustomerWishList(customerId)
        if (isInWishlist) {
            val colorType = if (isMatte) ColorType.MATTE else ColorType.GLOSSY
            wishList.put(color, colorType)

            if (colorType == ColorType.MATTE) {
                for (customerColor in wishList) {
                    if (customerColor.key != color) {
                        wishList[customerColor.key] = ColorType.GLOSSY
                    }
                }
            }
        } else {
            wishList.remove(color)
        }
    }

    fun getCustomerWishlist(customerId: Int): Map<Color, ColorType> = customerRepository.getCustomerWishList(customerId)

    fun getColors() = colorsRepository.getColors()
}