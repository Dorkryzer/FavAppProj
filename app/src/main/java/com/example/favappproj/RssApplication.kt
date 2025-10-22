package com.example.favappproj

import android.app.Application

class RssApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        FavAppRepository.initialize(this)
    }
}