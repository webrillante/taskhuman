package com.example.taskhuman.data


data class DataModel(
    val success: Boolean,
    val topicHeader: TopicHeader,
    val skills: List<Skill>,
    val isNextPage: Boolean,
    val message: String?,
    val blogMetaData: BlogMetaData? = null
)