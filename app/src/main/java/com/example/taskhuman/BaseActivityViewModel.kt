package com.example.taskhuman

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.taskhuman.api.ApiHelper
import com.example.taskhuman.data.FavouriteInput
import com.example.taskhuman.repository.MainRepository
import com.example.taskhuman.utils.Resource
import kotlinx.coroutines.Dispatchers

class BaseActivityViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getPhysicalFitnessData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getPhysicalFitnessData()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun addFavourite(input: FavouriteInput) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.addFavourite(input)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun removeFavourite(input: FavouriteInput) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.removeFavourite(input)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BaseActivityViewModel::class.java)) {
                return BaseActivityViewModel(MainRepository(apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }

    }
}