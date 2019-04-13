package com.github.gibbrich.paintfactory.domain.usecase

import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.utils.ListChangeType
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ColorsUseCaseTest {
    private lateinit var colorsRepository: ColorsRepository
    private lateinit var colorsUseCase: ColorsUseCase

    @Before
    fun setUp() {
        colorsRepository = mock()
        colorsUseCase = ColorsUseCase(colorsRepository)
    }

    @Test
    fun `addColor returns ShowAddColorFailedWarning if ColorsRepository#addColor returns null`() {
        whenever(colorsRepository.addColor(any())).thenReturn(null)
        val result = colorsUseCase.addColor(42)
        assertEquals(ColorsUseCase.Action.ShowAddColorFailedWarning, result)
    }

    @Test
    fun `addColor returns ChangeColorList with #colorId == lastIndex and #type == ListChangeType#ADD`() {
        val lastIndex = 10
        whenever(colorsRepository.addColor(any())).thenReturn(lastIndex)
        val result = colorsUseCase.addColor(42)
        val expected = ColorsUseCase.Action.ChangeColorList(lastIndex, ListChangeType.ADD)

        assertEquals(expected, result)
    }

    @Test
    fun `removeColor returns ChangeColorList with #colorId == #colorId and #type == ListChangeType#REMOVE`() {
        val colorId = 42
        val expected = ColorsUseCase.Action.ChangeColorList(colorId, ListChangeType.REMOVE)
        val result = colorsUseCase.removeColor(colorId)

        assertEquals(expected, result)
    }
}