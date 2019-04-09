package com.github.gibbrich.paintfactory.di

import com.github.gibbrich.paintfactory.domain.repository.ColorsRepository
import com.github.gibbrich.paintfactory.domain.repository.CustomerRespoitory
import dagger.Module
import dagger.Provides
import org.mockito.Mockito
import javax.inject.Singleton

@Module
class AppModuleMock {

    @Singleton
    @Provides
    fun provideColorsRepository(): ColorsRepository {
        return Mockito.mock(ColorsRepository::class.java)
    }

    @Singleton
    @Provides
    fun provideCustomerRepository(): CustomerRespoitory {
        return Mockito.mock(CustomerRespoitory::class.java)
    }
}