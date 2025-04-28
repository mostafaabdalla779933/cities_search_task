package com.moveandstore.citiessearchtask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moveandstore.citiessearchtask.data.City
import com.moveandstore.citiessearchtask.domin.GetCitiesUseCase
import com.moveandstore.citiessearchtask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesVM @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _cityList = MutableStateFlow<List<City>>(emptyList())
    val searchResults = _cityList.asStateFlow()

    private val _messageError = MutableStateFlow<String>("")
    val messageError = _messageError.asStateFlow()

    init {
        getCities()
    }


    fun onSearchQueryChanged(prefix: String) {

    }

    private fun getCities() {
        viewModelScope.launch {
            getCitiesUseCase().collect { result ->
                when(result){
                    is Resource.Success -> {
                        _cityList.value = result.data ?: emptyList()
                    }

                    is Resource.Error -> {
                        _messageError.value = result.message ?: "Unknown error"
                    }
                }
            }

        }
    }
}