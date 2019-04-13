package com.github.gibbrich.paintfactory.ui.viewModels

import android.arch.lifecycle.ViewModel
import com.github.gibbrich.paintfactory.di.Injector
import com.github.gibbrich.paintfactory.domain.models.ColorWithType
import com.github.gibbrich.paintfactory.domain.usecase.ColorsCalculationUseCase
import javax.inject.Inject

class ColorsCalculationViewModel : ViewModel() {

    @Inject
    internal lateinit var colorsCalculationUseCase: ColorsCalculationUseCase

    init {
        Injector.componentManager.colorsCalculationComponent.inject(this)
    }

    fun getColorsWithType(): List<ColorWithType> = colorsCalculationUseCase.getColorsWithType()
}