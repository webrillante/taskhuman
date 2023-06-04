package com.example.taskhuman.api

import com.example.taskhuman.data.FavouriteInput

class ApiHelper(private val apiService: ApiService) {

    suspend fun getPhysicalFitnessData() = apiService.getPhysicalFitnessData()
    suspend fun addFavourite(input: FavouriteInput) = apiService.addFavourite(input)
    suspend fun removeFavourite(input: FavouriteInput) = apiService.removeFavourite(input)
}