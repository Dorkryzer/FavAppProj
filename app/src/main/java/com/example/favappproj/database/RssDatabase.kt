package com.example.favappproj.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [DBitem::class], version = 1)
abstract class RssDatabase: RoomDatabase() {
    abstract fun RssDao(): RssDao
}