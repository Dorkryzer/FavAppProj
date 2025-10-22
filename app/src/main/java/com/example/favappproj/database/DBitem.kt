package com.example.favappproj.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DBitem (
    @PrimaryKey val id: String,
    val name: String,
    val url: String,
    val isFree: Boolean,
    var isMarked: Boolean
)
