package com.example.favappproj.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RssResponse (
    val feed: AppResponse
)
