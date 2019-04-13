package com.github.gibbrich.paintfactory.ui.viewModels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.gibbrich.paintfactory.di.Injector
import com.github.gibbrich.paintfactory.domain.usecase.ColorsUseCase
import javax.inject.Inject

class MainActivityViewModel : ViewModel() {

    val actions = MutableLiveData<ColorsUseCase.Action>()

    @Inject
    lateinit var colorsUseCase: ColorsUseCase

    init {
        Injector.componentManager.colorsComponent.inject(this)
    }

    fun onChooseColorButtonClicked() {
        actions.value = colorsUseCase.pickColor()
    }

    fun onSetupCustomersButtonClicked() {
        actions.value = colorsUseCase.setupCustomers()
    }

    fun onColorPicked(selectedColor: Int) {
        actions.value = colorsUseCase.addColor(selectedColor)
    }

    fun onColorRemoved(colorId: Int) {
        actions.value = colorsUseCase.removeColor(colorId)
    }

    fun getColors() = colorsUseCase.getColors()
}