package com.github.gibbrich.paintfactory.di

import com.github.gibbrich.paintfactory.data.ColorsDataRepository
import com.github.gibbrich.paintfactory.data.CustomerDataRepository
import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespoitory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideCustomerRepository(): CustomerRespoitory {
        return CustomerDataRepository()
    }

    @Singleton
    @Provides
    fun provideColorsRepository(): ColorsRepository {
        return ColorsDataRepository()
    }
}