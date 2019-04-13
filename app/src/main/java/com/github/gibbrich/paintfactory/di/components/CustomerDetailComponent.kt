package com.github.gibbrich.paintfactory.di.components

import com.github.gibbrich.paintfactory.di.module.CustomerDetailModule
import com.github.gibbrich.paintfactory.ui.viewModels.CustomerDetailViewModel
import dagger.Subcomponent

@Subcomponent(modules = [
    CustomerDetailModule::class
])
interface CustomerDetailComponent {
    fun inject(entry: CustomerDetailViewModel)

    @Subcomponent.Builder
    interface Builder {
        fun build(): CustomerDetailComponent
    }
}