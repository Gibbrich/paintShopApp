package com.github.gibbrich.paintfactory.di.module

import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespository
import com.github.gibbrich.paintfactory.domain.usecase.ColorsCalculationUseCase
import dagger.Module
import dagger.Provides

@Module
class ColorsCalculationModule {
    @Provides
    fun provideColorsCalculationUseCase(
        colorsRepository: ColorsRepository,
        customersRepository: CustomerRespository
    ): ColorsCalculationUseCase = ColorsCalculationUseCase(
        colorsRepository,
        customersRepository
    )
}