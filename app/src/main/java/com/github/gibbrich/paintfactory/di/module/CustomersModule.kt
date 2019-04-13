package com.github.gibbrich.paintfactory.di.module

import com.github.gibbrich.paintfactory.domain.repository.CustomerRespository
import com.github.gibbrich.paintfactory.domain.usecase.CustomersUseCase
import dagger.Module
import dagger.Provides

@Module
class CustomersModule {

    @Provides
    fun provideCustomersUseCase(
        customerRepository: CustomerRespository
    ): CustomersUseCase = CustomersUseCase(customerRepository)
}