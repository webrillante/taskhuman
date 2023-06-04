package com.example.taskhuman.data

data class BlogMetaData(
    val _id: String,
    val estReadTime: Int,
    val link: String,
    val title: String,
    val blogImageUrl: String?,
    val providerInfo: List<ProviderInfo>
)