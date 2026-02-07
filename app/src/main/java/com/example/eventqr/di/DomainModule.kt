package com.example.eventqr.di

import com.example.eventqr.domain.usecase.ParseQrToClientUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideParseQrToClientUseCase(): ParseQrToClientUseCase = ParseQrToClientUseCase()
}
