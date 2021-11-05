package com.jhoander.itunesmusic.itunesmusic.data.domain.entity

class SearchByTermParams(
    val term: String,
    val limit: Int,
    val mediaType: String = "music"
)