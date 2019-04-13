package com.github.gibbrich.paintfactory.di.components

import com.github.gibbrich.paintfactory.di.module.ColorsModule
import com.github.gibbrich.paintfactory.ui.viewModels.MainActivityViewModel
import dagger.Subcomponent

@Subcomponent(modules = [
    ColorsModule::class
])
interface ColorsComponent {
    fun inject(entry: MainActivityViewModel)

    @Subcomponent.Builder
    interface Builder {
        fun build(): ColorsComponent
    }
}