package com.example.favappproj

import android.content.Context
import androidx.room.Room
import com.example.favappproj.api.ListItem
import com.example.favappproj.api.RssApi
import com.example.favappproj.database.DBitem
import com.example.favappproj.database.RssDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class FavAppRepository private constructor(context: Context) {

    private val database: RssDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            RssDatabase::class.java,
            "apps-database"
        ).build()

    fun getFreeApps(): Flow<List<DBitem>> = database.RssDao().getFreeApps()
    fun getPaidApps(): Flow<List<DBitem>> = database.RssDao().getPaidApps()

    fun getMarkedApps():Flow<List<DBitem>> = database.RssDao().getMarkedApps()
    fun updateApp(dBitem: DBitem){
        GlobalScope.launch {
            database.RssDao().updateApp(dBitem)
        }
    }
    fun insertApp(dBitem: DBitem){
        GlobalScope.launch {
            database.RssDao().insertApp(dBitem)
        }
    }
    companion object{
        private var INSTANCE: FavAppRepository? = null
        fun initialize(context: Context){
            if(INSTANCE==null){
                INSTANCE= FavAppRepository(context)
            }
        }
        fun get(): FavAppRepository{
            return  INSTANCE?:
            throw IllegalStateException("you need to initialize the repo")
        }
    }

    private val rssApi: RssApi
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://rss.marketingtools.apple.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        rssApi = retrofit.create()
    }
    suspend fun fetchFreeApps() : List<ListItem> = rssApi.fetchFreeApps().feed.listItems
    suspend fun fetchPaidApps() : List<ListItem> = rssApi.fetchPaidApps().feed.listItems
}
