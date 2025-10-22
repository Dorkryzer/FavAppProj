package com.example.favappproj.api

import retrofit2.http.GET

interface RssApi {
    @GET("/api/v2/us/apps/top-free/10/apps.json")
    suspend fun fetchFreeApps(): RssResponse

    @GET("/api/v2/us/apps/top-paid/25/apps.json")
    suspend fun fetchPaidApps(): RssResponse
}