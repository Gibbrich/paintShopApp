package com.github.gibbrich.paintfactory.di

import com.github.gibbrich.paintfactory.di.components.AppComponent

object Injector {
    lateinit var componentManager: ComponentManager

    fun init(appComponent: AppComponent) {
        componentManager = ComponentManager(appComponent)
    }
}