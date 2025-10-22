package com.example.favappproj.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RssDao {
    @Query("SELECT * FROM DBitem WHERE isFree = 1")
    fun getFreeApps(): Flow<List<DBitem>>

    @Query("SELECT * FROM DBitem WHERE isFree = 0")
    fun getPaidApps(): Flow<List<DBitem>>

    @Query("SELECT * FROM DBitem WHERE isMarked = 1")
    fun getMarkedApps():Flow<List<DBitem>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertApp(dBitem: DBitem)

    @Update
    suspend fun updateApp(dBitem: DBitem)
}