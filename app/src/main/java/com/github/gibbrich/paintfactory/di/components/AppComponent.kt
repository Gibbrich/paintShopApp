package com.github.gibbrich.paintfactory.di.components

import com.github.gibbrich.paintfactory.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class
])
interface AppComponent {
    fun colorsComponentBuilder(): ColorsComponent.Builder
    fun customersComponentBuilder(): CustomersComponent.Builder
    fun customerDetailComponentBuilder(): CustomerDetailComponent.Builder
    fun colorsCalculationComponentBuilder(): ColorsCalculationComponent.Builder
}