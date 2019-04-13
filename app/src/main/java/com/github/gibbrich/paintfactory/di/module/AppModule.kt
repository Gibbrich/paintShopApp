package com.github.gibbrich.paintfactory.di.module

import com.github.gibbrich.paintfactory.data.ColorsDataRepository
import com.github.gibbrich.paintfactory.data.CustomerDataRepository
import com.github.gibbrich.paintfactory.di.components.ColorsCalculationComponent
import com.github.gibbrich.paintfactory.di.components.ColorsComponent
import com.github.gibbrich.paintfactory.di.components.CustomerDetailComponent
import com.github.gibbrich.paintfactory.di.components.CustomersComponent
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [
    ColorsComponent::class,
    CustomersComponent::class,
    ColorsCalculationComponent::class,
    CustomerDetailComponent::class
])
class AppModule {

    @Singleton
    @Provides
    fun provideCustomerRepository(): CustomerRespository {
        return CustomerDataRepository()
    }

    @Singleton
    @Provides
    fun provideColorsRepository(): ColorsRepository {
        return ColorsDataRepository()
    }
}