package com.moveandstore.citiessearchtask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moveandstore.citiessearchtask.data.model.City
import com.moveandstore.citiessearchtask.domin.usecase.CitySearchUseCase
import com.moveandstore.citiessearchtask.domin.usecase.GetCitiesUseCase
import com.moveandstore.citiessearchtask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesVM @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val citySearchUseCase: CitySearchUseCase
) : ViewModel() {

    private val _cityList = MutableStateFlow<List<City>>(emptyList())
    val searchResults = _cityList.asStateFlow()

    private val _messageError = MutableStateFlow<String>("")
    val messageError = _messageError.asStateFlow()

    lateinit var citiesList: List<City>

    init {
        getCities()
    }


    fun onSearchQueryChanged(prefix: String) {
        viewModelScope.launch {
            if (prefix.isEmpty()) {
                _cityList.value = citiesList
            } else {
                _cityList.value = citySearchUseCase(prefix)
            }
        }
    }

    private fun getCities() {
        viewModelScope.launch {
            getCitiesUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        citiesList = result.data ?: emptyList()
                        _cityList.value = citiesList
                        citySearchUseCase.addCities(result.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        _messageError.value = result.message ?: "Unknown error"
                    }
                }
            }
        }
    }


}