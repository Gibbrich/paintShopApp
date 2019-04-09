package com.github.gibbrich.paintfactory

import android.app.Application
import com.github.gibbrich.paintfactory.di.AppComponent
import com.github.gibbrich.paintfactory.di.DaggerAppComponent

open class PaintShopApp: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = createComponent()
    }

    protected open fun createComponent(): AppComponent {
        return DaggerAppComponent.builder().build()
    }
}