package com.github.gibbrich.paintfactory.di.module

import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespository
import com.github.gibbrich.paintfactory.domain.usecase.CustomerDetailUseCase
import dagger.Module
import dagger.Provides

@Module
class CustomerDetailModule {

    @Provides
    fun provideCustomerDetailUseCase(
        customerRespository: CustomerRespository,
        colorsRepository: ColorsRepository
    ): CustomerDetailUseCase = CustomerDetailUseCase(
        customerRespository,
        colorsRepository
    )
}