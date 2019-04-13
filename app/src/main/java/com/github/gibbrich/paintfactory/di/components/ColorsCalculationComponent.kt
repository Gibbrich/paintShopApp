package com.github.gibbrich.paintfactory.di.components

import com.github.gibbrich.paintfactory.di.module.ColorsCalculationModule
import com.github.gibbrich.paintfactory.ui.viewModels.ColorsCalculationViewModel
import dagger.Subcomponent

@Subcomponent(modules = [
    ColorsCalculationModule::class
])
interface ColorsCalculationComponent {
    fun inject(entry: ColorsCalculationViewModel)

    @Subcomponent.Builder
    interface Builder {
        fun build(): ColorsCalculationComponent
    }
}