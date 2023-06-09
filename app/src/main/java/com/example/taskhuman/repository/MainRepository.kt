package com.example.taskhuman.repository

import com.example.taskhuman.api.ApiHelper
import com.example.taskhuman.data.FavouriteInput

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getDiscoverData(paramValue: String) = apiHelper.getDiscoverData(paramValue)
    suspend fun addFavourite(input: FavouriteInput) = apiHelper.addFavourite(input)
    suspend fun removeFavourite(input: FavouriteInput) = apiHelper.removeFavourite(input)
}