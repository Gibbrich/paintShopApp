package com.github.gibbrich.paintfactory

import android.app.Application
import com.github.gibbrich.paintfactory.di.DaggerAppComponent

class PaintShopApp: Application() {
    val appComponent by lazy { DaggerAppComponent.builder().build() }
}