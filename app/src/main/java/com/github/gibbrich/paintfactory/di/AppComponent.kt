package com.github.gibbrich.paintfactory.di

import com.github.gibbrich.paintfactory.ui.viewModels.ColorsCalculationViewModel
import com.github.gibbrich.paintfactory.ui.viewModels.CustomerDetailViewModel
import com.github.gibbrich.paintfactory.ui.viewModels.CustomersActivityViewModel
import com.github.gibbrich.paintfactory.ui.viewModels.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class
])
interface AppComponent {
    fun inject(entry: CustomersActivityViewModel)
    fun inject(entry: CustomerDetailViewModel)
    fun inject(entry: ColorsCalculationViewModel)
    fun inject(entry: MainActivityViewModel)
}