package com.moveandstore.citiessearchtask.domin.repository


import com.moveandstore.citiessearchtask.data.City
import com.moveandstore.citiessearchtask.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CityRepository {
     fun getCities(): Flow<Resource<List<City>>>
}