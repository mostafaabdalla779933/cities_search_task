package com.moveandstore.citiessearchtask.domin.usecase

import com.moveandstore.citiessearchtask.data.model.City
import com.moveandstore.citiessearchtask.data.trie.CityTrie
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


@ViewModelScoped
class CitySearchUseCase @Inject constructor(private val citiesTrie: CityTrie) {

    operator fun invoke(prefix: String): List<City> {
        return citiesTrie.searchPrefix(prefix.lowercase())
    }

    fun addCities(cities: List<City>) {
        cities.forEach {
            citiesTrie.insert(it)
        }
    }

}