package com.example.favappproj

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favappproj.api.ApiToDBFree
import com.example.favappproj.api.ApiToDBPaid
import com.example.favappproj.api.ListItem
import com.example.favappproj.database.DBitem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel: ViewModel() {
    private val favAppRepository = FavAppRepository.get()
    private val _favoriteItems: MutableStateFlow<List<DBitem>> =
        MutableStateFlow(emptyList())
    val favoriteItems: StateFlow<List<DBitem>>
        get() = _favoriteItems.asStateFlow()
    private val _freeDBItems: MutableStateFlow<List<DBitem>> =
        MutableStateFlow(emptyList())
    val freeDBItems: StateFlow<List<DBitem>>
        get() = _freeDBItems.asStateFlow()
    private val _paidDBItems: MutableStateFlow<List<DBitem>> =
        MutableStateFlow(emptyList())
    val paidDBItems: StateFlow<List<DBitem>>
        get() = _paidDBItems.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val freeItems = favAppRepository.fetchFreeApps()
                val paidItems = favAppRepository.fetchPaidApps()

                for (app in freeItems){
                    val inserted = app.ApiToDBFree()
                    favAppRepository.insertApp(inserted)
                }
                for (app in paidItems){
                    val inserted = app.ApiToDBPaid()
                    favAppRepository.insertApp(inserted)
                }


            }catch (ex: Exception)
            {
                Log.d("itemss", "Failed to fetch items")
            }

        }
        viewModelScope.launch {
            favAppRepository.getFreeApps().collect {
                _freeDBItems.value = it
            }
        }
        viewModelScope.launch {
            favAppRepository.getPaidApps().collect {
                _paidDBItems.value = it
            }
        }
        viewModelScope.launch {
            favAppRepository.getMarkedApps().collect {
                _favoriteItems.value = it
            }
        }
    }
    fun updateDbItem(item: DBitem){
        favAppRepository.updateApp(item)
    }
}