package com.moveandstore.citiessearchtask.data.repo

import android.content.Context
import com.moveandstore.citiessearchtask.R
import com.moveandstore.citiessearchtask.data.City
import com.moveandstore.citiessearchtask.domin.repository.CityRepository
import com.moveandstore.citiessearchtask.utils.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(@ApplicationContext val context: Context) :
    CityRepository {
    override fun getCities(): Flow<Resource<List<City>>> = flow {
        try {
            val json = Json {
                ignoreUnknownKeys = true
            }
            val jsonString = context.resources.openRawResource(R.raw.cities)
                .bufferedReader()
                .use { it.readText() }
            val cities = json.decodeFromString<List<City>>(jsonString)
            emit(Resource.Success(cities.sortedWith(
                compareBy(
                    { !(it.name.firstOrNull()?.isLetter() ?: true) },
                    { it.name.lowercase() }
                )
            )))
        } catch (e: Exception) {
            emit(Resource.Error("something went wrong"))
            e.printStackTrace()
        }
    }


}
