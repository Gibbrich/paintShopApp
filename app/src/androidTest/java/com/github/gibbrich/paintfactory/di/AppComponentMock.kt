package com.github.gibbrich.paintfactory.di

import com.github.gibbrich.paintfactory.ui.ColorsCalculationActivityTest
import com.github.gibbrich.paintfactory.ui.CustomerDetailActivityTest
import com.github.gibbrich.paintfactory.ui.CustomersActivityTest
import com.github.gibbrich.paintfactory.ui.MainActivityTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModuleMock::class
])
interface AppComponentMock: AppComponent {
    fun inject(entry: MainActivityTest)
    fun inject(entry: CustomersActivityTest)
    fun inject(entry: CustomerDetailActivityTest)
    fun inject(entry: ColorsCalculationActivityTest)
}