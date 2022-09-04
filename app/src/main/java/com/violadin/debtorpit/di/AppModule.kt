package com.violadin.debtorpit.di

import com.violadin.debtorpit.navigation.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideNavigationManager(): NavigationManager = NavigationManager()
}