package com.example.favappproj.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AppResponse (
    @Json(name = "results") val listItems: List<ListItem>
)
