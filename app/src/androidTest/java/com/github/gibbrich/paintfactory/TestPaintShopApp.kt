package com.github.gibbrich.paintfactory

import com.github.gibbrich.paintfactory.di.AppComponent
import com.github.gibbrich.paintfactory.di.DaggerAppComponentMock

class TestPaintShopApp: PaintShopApp() {

    override fun createComponent(): AppComponent {
        return DaggerAppComponentMock.builder().build()
    }
}