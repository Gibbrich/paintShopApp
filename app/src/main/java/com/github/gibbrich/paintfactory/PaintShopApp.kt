package com.github.gibbrich.paintfactory

import android.app.Application
import com.github.gibbrich.paintfactory.di.components.AppComponent
import com.github.gibbrich.paintfactory.di.Injector
import com.github.gibbrich.paintfactory.di.components.DaggerAppComponent

open class PaintShopApp: Application() {

    override fun onCreate() {
        super.onCreate()

        Injector.init(createComponent())
    }

    protected open fun createComponent(): AppComponent {
        return DaggerAppComponent.builder().build()
    }
}