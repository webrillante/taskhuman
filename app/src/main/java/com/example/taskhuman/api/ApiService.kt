package com.example.taskhuman.api

import com.example.taskhuman.data.DataModel
import com.example.taskhuman.data.FavouriteInput
import com.example.taskhuman.data.FavouriteResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("discover/topicDetails/{paramValue}")
    suspend fun getDiscoverData(@Path("paramValue") paramValue: String): DataModel

    @POST("favorite/add")
    suspend fun addFavourite(@Body data: FavouriteInput): FavouriteResponse

    @POST("favorite/remove")
    suspend fun removeFavourite(@Body data: FavouriteInput): FavouriteResponse
}