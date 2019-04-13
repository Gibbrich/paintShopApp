package com.github.gibbrich.paintfactory

import com.github.gibbrich.paintfactory.di.components.AppComponent
import com.github.gibbrich.paintfactory.di.DaggerAppComponentMock

class TestPaintShopApp: PaintShopApp() {

    public override fun createComponent(): AppComponent {
        return DaggerAppComponentMock.builder().build()
    }
}