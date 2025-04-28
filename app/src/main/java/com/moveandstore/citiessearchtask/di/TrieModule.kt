package com.moveandstore.citiessearchtask.di

import com.moveandstore.citiessearchtask.data.trie.CityTrie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
internal class TrieModule {

    @Provides
    fun provideTrie() : CityTrie = CityTrie()
}