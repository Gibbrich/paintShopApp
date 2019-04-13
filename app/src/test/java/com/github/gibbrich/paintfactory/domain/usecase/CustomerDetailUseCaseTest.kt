package com.github.gibbrich.paintfactory.domain.usecase

import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.models.ColorType
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CustomerDetailUseCaseTest {
    private lateinit var customerRepository: CustomerRespository
    private lateinit var colorsRepository: ColorsRepository
    private lateinit var useCase: CustomerDetailUseCase

    @Before
    fun setUp() {
        customerRepository = mock()
        colorsRepository = mock()
        useCase = CustomerDetailUseCase(customerRepository, colorsRepository)
    }

    @Test
    fun `updateWishlist removes color from wishlist if #isInWishlist == false`() {
        val color1 = Color(1)
        val color2 = Color(2)
        val color3 = Color(3)

        val mockCustomerWishlist = mutableMapOf(
            color1 to ColorType.GLOSSY,
            color2 to ColorType.MATTE,
            color3 to ColorType.GLOSSY
        )

        val customerId = 42
        whenever(customerRepository.getCustomerWishList(customerId)).thenReturn(mockCustomerWishlist)

        useCase.updateWishlist(customerId, false, color2, true)

        val expected = mutableMapOf(
            color1 to ColorType.GLOSSY,
            color3 to ColorType.GLOSSY
        )

        val result = customerRepository.getCustomerWishList(customerId)

        assertEquals(expected, result)
    }

    @Test
    fun `updateWishlist just adds color to customer wishlist if #isInWishlist == true and its type == ColorType#GLOSSY`() {
        val color1 = Color(1)
        val color2 = Color(2)
        val color3 = Color(3)

        val mockCustomerWishlist = mutableMapOf(
            color1 to ColorType.GLOSSY,
            color2 to ColorType.MATTE,
            color3 to ColorType.GLOSSY
        )

        val customerId = 42
        whenever(customerRepository.getCustomerWishList(customerId)).thenReturn(mockCustomerWishlist)

        val newColor = mock<Color>()

        useCase.updateWishlist(customerId, true, newColor, false)

        val expected = mutableMapOf(
            color1 to ColorType.GLOSSY,
            color2 to ColorType.MATTE,
            color3 to ColorType.GLOSSY,
            newColor to ColorType.GLOSSY
        )

        val result = customerRepository.getCustomerWishList(customerId)

        assertEquals(expected, result)
    }

    @Test
    fun `updateWishlist adds color to wishlist and changes other colors type to glossy if #isInWishlist == true and its type == ColorType#MATTE`() {
        val color1 = Color(1)
        val color2 = Color(2)
        val color3 = Color(3)

        val mockCustomerWishlist = mutableMapOf(
            color1 to ColorType.GLOSSY,
            color2 to ColorType.MATTE,
            color3 to ColorType.GLOSSY
        )

        val customerId = 42
        whenever(customerRepository.getCustomerWishList(customerId)).thenReturn(mockCustomerWishlist)

        val newColor = mock<Color>()

        useCase.updateWishlist(customerId, true, newColor, true)

        val expected = mutableMapOf(
            color1 to ColorType.GLOSSY,
            color2 to ColorType.GLOSSY,
            color3 to ColorType.GLOSSY,
            newColor to ColorType.MATTE
        )

        val result = customerRepository.getCustomerWishList(customerId)

        assertEquals(expected, result)
    }
}