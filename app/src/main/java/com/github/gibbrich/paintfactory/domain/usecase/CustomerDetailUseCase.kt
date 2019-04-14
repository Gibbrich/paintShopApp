package com.github.gibbrich.paintfactory.domain.usecase

import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.models.ColorType
import com.github.gibbrich.paintfactory.domain.models.Customer
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespository

class CustomerDetailUseCase(
    val customerRepository: CustomerRespository,
    val colorsRepository: ColorsRepository
) {
    /**
     * Add/delete [color] to/from [Customer.wishlist], depending on [isInWishlist] flag.
     *
     * @param customerId [Customer] position in list, which [Customer.wishList] will be modified
     * @param isInWishlist add [color] to [Customer.wishList] if true; remove otherwise
     * @param color [Color] which will be added to [Customer.wishList]
     * @param isMatte type of [color], which will be added to [Customer.wishList].
     * [ColorType.MATTE] if true, [ColorType.GLOSSY] otherwise
     */
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