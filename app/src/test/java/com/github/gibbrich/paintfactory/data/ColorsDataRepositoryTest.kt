package com.github.gibbrich.paintfactory.data

import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class ColorsDataRepositoryTest {

    private lateinit var colorsRepository: ColorsRepository

    @Before
    fun setUp() {
        colorsRepository = ColorsDataRepository()
    }

    @Test
    fun `add color which is not in repo returns last index`() {
        val result = colorsRepository.addColor(mock())
        assertEquals(0, result)
    }

    @Test
    fun `add color which is in repo returns null`() {
        val color = mock<Color>()
        colorsRepository.addColor(color)
        val result = colorsRepository.addColor(color)
        assertNull(result)
    }
}