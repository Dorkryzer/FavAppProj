package com.example.favappproj.api

import com.example.favappproj.database.DBitem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListItem (
    val id : String,
    val name: String,
    @Json(name = "artworkUrl100") val url: String
)
fun ListItem.ApiToDBFree(): DBitem{
    return DBitem(this.id,this.name,this.url, isFree = true, isMarked = false)
}
fun ListItem.ApiToDBPaid(): DBitem{
    return DBitem(this.id,this.name,this.url, isFree = false, isMarked = false)
}

