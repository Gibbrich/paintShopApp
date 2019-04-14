package com.github.gibbrich.paintfactory.domain.usecase

import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.utils.ListChangeType

class ColorsUseCase(
    private val colorsRepository: ColorsRepository
) {
    fun pickColor() = ColorsUseCase.Action.ShowColorPicker

    fun setupCustomers() = ColorsUseCase.Action.SwitchToCustomersScreen

    /**
     * Add color to the list if there is no such color already.
     *
     * @return [ColorsUseCase.Action.ShowAddColorFailedWarning] if there is such color in list already;
     * [ColorsUseCase.Action.ChangeColorList] otherwise.
     */
    fun addColor(selectedColor: Int): ColorsUseCase.Action {
        val color = Color(selectedColor)
        val colorId = colorsRepository.addColor(color)

        return if (colorId == null) {
            ColorsUseCase.Action.ShowAddColorFailedWarning
        } else {
            ColorsUseCase.Action.ChangeColorList(colorId, ListChangeType.ADD)
        }
    }

    /**
     * Remove color from list.
     *
     * @param colorId - position of color in the list
     */
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