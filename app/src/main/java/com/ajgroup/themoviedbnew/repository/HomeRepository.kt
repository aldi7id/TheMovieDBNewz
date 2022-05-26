package com.ajgroup.themoviedbnew.repository

import com.ajgroup.themoviedbnew.data.api.ApiService

class HomeRepository(private val apiService: ApiService) {
     fun getDiscoverMovies() = apiService.getDiscoverMovie()
}