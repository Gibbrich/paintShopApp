package com.github.gibbrich.paintfactory.ui.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.github.gibbrich.paintfactory.PaintShopApp
import com.github.gibbrich.paintfactory.data.ColorsRepository
import com.github.gibbrich.paintfactory.domain.Color
import com.github.gibbrich.paintfactory.utils.ListChangeType
import javax.inject.Inject

class MainActivityViewModel(app: Application): AndroidViewModel(app) {

    val actions = MutableLiveData<Action>()

    @Inject
    lateinit var colorsRepository: ColorsRepository

    init {
        getApplication<PaintShopApp>().appComponent.inject(this)
    }

    fun onChooseColorButtonClicked() {
        actions.value = Action.ShowColorPicker
    }

    fun onSetupCustomersButtonClicked() {
        actions.value = Action.SwitchToCustomersScreen
    }

    fun onColorPicked(selectedColor: Int) {
        val color = Color(selectedColor)
        colorsRepository.colors.add(color)
        actions.value = Action.ChangeColorList(getColors().lastIndex, ListChangeType.ADD)
    }

    fun onColorRemoved(colorId: Int) {
        colorsRepository.colors.removeAt(colorId)
        actions.value = Action.ChangeColorList(colorId, ListChangeType.REMOVE)
    }

    fun getColors() = colorsRepository.colors

    sealed class Action {
        object SwitchToCustomersScreen: Action()
        object ShowColorPicker: Action()
        data class ChangeColorList(val colorId: Int, val type: ListChangeType): Action()
    }
}