package com.github.gibbrich.paintfactory.di.module

import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.domain.usecase.ColorsUseCase
import dagger.Module
import dagger.Provides

@Module
class ColorsModule {

    @Provides
    fun provideColorsUseCase(
        colorsRepository: ColorsRepository
    ): ColorsUseCase = ColorsUseCase(colorsRepository)
}