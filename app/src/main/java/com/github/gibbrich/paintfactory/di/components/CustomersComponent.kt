package com.github.gibbrich.paintfactory.di.components

import com.github.gibbrich.paintfactory.di.module.CustomersModule
import com.github.gibbrich.paintfactory.ui.viewModels.CustomersActivityViewModel
import dagger.Subcomponent

@Subcomponent(modules = [
    CustomersModule::class
])
interface CustomersComponent {
    fun inject(entry: CustomersActivityViewModel)

    @Subcomponent.Builder
    interface Builder {
        fun build(): CustomersComponent
    }
}