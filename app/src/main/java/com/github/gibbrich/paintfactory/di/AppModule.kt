package com.github.gibbrich.paintfactory.di

import com.github.gibbrich.paintfactory.data.CustomerRepository
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideCustomerRepository(): CustomerRepository {
        return CustomerRepository()
    }
}