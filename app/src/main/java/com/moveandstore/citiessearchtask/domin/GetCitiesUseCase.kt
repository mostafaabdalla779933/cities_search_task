package com.moveandstore.citiessearchtask.domin

import com.moveandstore.citiessearchtask.data.City
import com.moveandstore.citiessearchtask.domin.repository.CityRepository
import com.moveandstore.citiessearchtask.utils.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@ViewModelScoped
class GetCitiesUseCase @Inject constructor(private val repository: CityRepository) {
    operator fun invoke(): Flow<Resource<List<City>>> = repository.getCities()
}