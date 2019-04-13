package com.github.gibbrich.paintfactory.di

import com.github.gibbrich.paintfactory.di.components.*

class ComponentManager(private val appComponent: AppComponent) {
    val colorsComponent: ColorsComponent by lazy {
        appComponent
            .colorsComponentBuilder()
            .build()
    }

    val customersComponent: CustomersComponent by lazy {
        appComponent
            .customersComponentBuilder()
            .build()
    }

    val customerDetailComponent: CustomerDetailComponent by lazy {
        appComponent
            .customerDetailComponentBuilder()
            .build()
    }

    val colorsCalculationComponent: ColorsCalculationComponent by lazy {
        appComponent
            .colorsCalculationComponentBuilder()
            .build()
    }
}