package com.ajgroup.themoviedbnew.repository

import com.ajgroup.themoviedbnew.data.api.ApiService

//private val userPref: UserDataStoreManager
class HomeRepository(private val apiService: ApiService) {
     fun getDiscoverMovies() = apiService.getDiscoverMovie()
//     fun getNama() = userPref.getNama.asLiveData()
    suspend fun getAllPlayingNow() = apiService.getAllPlayingNow()
}