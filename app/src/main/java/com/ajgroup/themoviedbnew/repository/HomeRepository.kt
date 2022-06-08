package com.ajgroup.themoviedbnew.repository

import androidx.lifecycle.asLiveData
import com.ajgroup.themoviedbnew.data.api.ApiService
import com.ajgroup.themoviedbnew.data.local.UserDataStoreManager

class HomeRepository(private val apiService: ApiService, private val userPref: UserDataStoreManager) {
     fun getDiscoverMovies() = apiService.getDiscoverMovie()
     fun getNama() = userPref.getNama.asLiveData()
    suspend fun getAllPlayingNow() = apiService.getAllPlayingNow()
}