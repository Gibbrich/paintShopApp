package com.github.gibbrich.paintfactory.di

import com.github.gibbrich.paintfactory.ui.ColorsCalculationActivity
import com.github.gibbrich.paintfactory.ui.CustomerDetailActivity
import com.github.gibbrich.paintfactory.ui.CustomersActivity
import com.github.gibbrich.paintfactory.ui.MainActivity
import com.github.gibbrich.paintfactory.ui.viewModels.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class
])
interface AppComponent {
    fun inject(entry: CustomersActivity)
    fun inject(entry: CustomerDetailActivity)
    fun inject(entry: ColorsCalculationActivity)

    fun inject(entry: MainActivityViewModel)
}