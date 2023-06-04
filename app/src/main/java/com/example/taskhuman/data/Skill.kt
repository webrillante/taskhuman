package com.example.taskhuman.data

data class Skill(
    val tileName: String,
    val displayTileName: String,
    val type: String,
    val dictionaryName: String?,
    val tileBackground: String?,
    val skillIcon: String?,
    val availability: Availability?,
    val moreProvidersAvailable: Boolean,
    val providerInfo: List<ProviderInfo>,
    var isFavorite: Boolean,
    val columns: Int
) {
    var added: Boolean? = null
}