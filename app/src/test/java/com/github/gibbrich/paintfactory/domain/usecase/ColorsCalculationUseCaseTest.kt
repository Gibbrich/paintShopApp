package com.github.gibbrich.paintfactory.domain.usecase

import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.models.ColorType
import com.github.gibbrich.paintfactory.domain.models.ColorWithType
import com.github.gibbrich.paintfactory.domain.models.Customer
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ColorsCalculationUseCaseTest {
    private lateinit var customerRepository: CustomerRespository
    private lateinit var colorsRepository: ColorsRepository

    private lateinit var useCase: ColorsCalculationUseCase

    @Before
    fun setUp() {
        customerRepository = mock()
        colorsRepository = mock()
        useCase = ColorsCalculationUseCase(colorsRepository, customerRepository)
    }

    @Test
    fun `getColorsWithType returns list of colors with types if there is solution`() {
        val color1 = Color(0)
        val color2 = Color(1)
        val color3 = Color(2)

        val colors = listOf(color1, color2, color3)

        whenever(colorsRepository.getColors()).thenReturn(colors)
        whenever(colorsRepository.getColorId(color1)).thenReturn(0)
        whenever(colorsRepository.getColorId(color2)).thenReturn(1)
        whenever(colorsRepository.getColorId(color3)).thenReturn(2)

        val customer1 = Customer(mutableMapOf(
            color1 to ColorType.MATTE
        ))

        val customer2 = Customer(mutableMapOf(
            color2 to ColorType.GLOSSY,
            color3 to ColorType.MATTE
        ))

        val customer3 = Customer(mutableMapOf(
            color1 to ColorType.GLOSSY,
            color2 to ColorType.MATTE
        ))

        val customers = listOf(customer1, customer2, customer3)

        whenever(customerRepository.getCustomers()).thenReturn(customers)

        val expected = listOf(
            ColorWithType(0, ColorType.MATTE),
            ColorWithType(1, ColorType.MATTE),
            ColorWithType(2, ColorType.MATTE)
        )

        val result = useCase.getColorsWithType()

        assertEquals(expected, result)
    }

    @Test
    fun `getColorsWithType returns empty list if there is no solution`() {
        val color1 = Color(0)
        val color2 = Color(1)
        val color3 = Color(2)

        val colors = listOf(color1, color2, color3)

        whenever(colorsRepository.getColors()).thenReturn(colors)
        whenever(colorsRepository.getColorId(color1)).thenReturn(0)
        whenever(colorsRepository.getColorId(color2)).thenReturn(1)
        whenever(colorsRepository.getColorId(color3)).thenReturn(2)

        val customer1 = Customer(mutableMapOf(
            color1 to ColorType.MATTE
        ))

        val customer2 = Customer(mutableMapOf(
            color1 to ColorType.GLOSSY
        ))

        val customers = listOf(customer1, customer2)

        whenever(customerRepository.getCustomers()).thenReturn(customers)

        val result = useCase.getColorsWithType()

        assertTrue(result.isEmpty())
    }
}