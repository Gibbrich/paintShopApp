package com.github.gibbrich.paintfactory.di

import com.github.gibbrich.paintfactory.data.ColorsRepository
import com.github.gibbrich.paintfactory.data.CustomerRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideCustomerRepository(): CustomerRepository {
        return CustomerRepository()
    }

    @Singleton
    @Provides
    fun provideColorsRepository(): ColorsRepository {
        return ColorsRepository()
    }
}