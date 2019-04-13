package com.github.gibbrich.paintfactory.domain.usecase

import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.utils.ListChangeType

class ColorsUseCase(
    private val colorsRepository: ColorsRepository
) {
    fun pickColor() = ColorsUseCase.Action.ShowColorPicker

    fun setupCustomers() = ColorsUseCase.Action.SwitchToCustomersScreen

    fun addColor(selectedColor: Int): ColorsUseCase.Action {
        val color = Color(selectedColor)
        val colorId = colorsRepository.addColor(color)

        return if (colorId == null) {
            ColorsUseCase.Action.ShowAddColorFailedWarning
        } else {
            ColorsUseCase.Action.ChangeColorList(colorId, ListChangeType.ADD)
        }
    }

    fun removeColor(colorId: Int): Action.ChangeColorList {
        colorsRepository.removeColor(colorId)
        return ColorsUseCase.Action.ChangeColorList(colorId, ListChangeType.REMOVE)
    }

    fun getColors() = colorsRepository.getColors()

    sealed class Action {
        object SwitchToCustomersScreen: Action()
        object ShowColorPicker: Action()
        object ShowAddColorFailedWarning: Action()
        data class ChangeColorList(val colorId: Int, val type: ListChangeType): Action()
    }
}