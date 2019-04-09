package com.github.gibbrich.paintfactory.ui.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.github.gibbrich.paintfactory.PaintShopApp
import com.github.gibbrich.paintfactory.data.ColorsDataRepository
import com.github.gibbrich.paintfactory.domain.models.Color
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
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
        val colorId = colorsRepository.addColor(color)

        colorId?.let {
            actions.value = Action.ChangeColorList(it, ListChangeType.ADD)
        } ?: kotlin.run {
            actions.value = Action.ShowAddColorFailedWarning
        }
    }

    fun onColorRemoved(colorId: Int) {
        colorsRepository.removeColor(colorId)
        actions.value = Action.ChangeColorList(colorId, ListChangeType.REMOVE)
    }

    fun getColors() = colorsRepository.getColors()

    sealed class Action {
        object SwitchToCustomersScreen: Action()
        object ShowColorPicker: Action()
        object ShowAddColorFailedWarning: Action()
        data class ChangeColorList(val colorId: Int, val type: ListChangeType): Action()
    }
}