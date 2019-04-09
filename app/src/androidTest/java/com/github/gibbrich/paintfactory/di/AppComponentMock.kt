package com.github.gibbrich.paintfactory.di

import com.github.gibbrich.paintfactory.ui.MainActivityTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModuleMock::class
])
interface AppComponentMock: AppComponent {
    fun inject(entry: MainActivityTest)
}