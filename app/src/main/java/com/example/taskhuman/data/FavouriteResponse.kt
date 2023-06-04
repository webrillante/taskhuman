package com.example.taskhuman.data

data class Favorite(
    val providerId: String?,
    val favoriteBy: String,
    val dictionaryName: String,
    val discoverItemId: String?,
    val consumerGroup: String?,
    val providerGroup: String?,
    val id: String,
    val categoryName: String,
    val consumerId: Int,
    val createdAt: String,
    val updatedAt: String,
    val v: Int
)

data class FavouriteResponse(
    val success: Boolean,
    val favorite: Favorite,
    val message: String,
    val showFavoriteToast: Boolean
)