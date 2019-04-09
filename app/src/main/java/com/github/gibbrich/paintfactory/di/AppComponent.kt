package com.github.gibbrich.paintfactory.di

import com.github.gibbrich.paintfactory.ui.ColorsCalculationActivity
import com.github.gibbrich.paintfactory.ui.CustomerDetailActivity
import com.github.gibbrich.paintfactory.ui.CustomersActivity
import com.github.gibbrich.paintfactory.ui.MainActivity
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
    fun inject(entry: ColorsCalculationActivity)
    fun inject(entry: MainActivityViewModel)
}