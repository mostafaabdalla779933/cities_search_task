package com.moveandstore.citiessearchtask.di

import com.moveandstore.citiessearchtask.data.repo.CityRepositoryImpl
import com.moveandstore.citiessearchtask.domin.repository.CityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module that holds Repository classes
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repository : CityRepositoryImpl) : CityRepository

}