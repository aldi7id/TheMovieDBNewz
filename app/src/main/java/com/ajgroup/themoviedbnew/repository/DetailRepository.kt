package com.ajgroup.themoviedbnew.repository

import com.ajgroup.themoviedbnew.data.api.ApiService

//private val favoriteDao: FavoriteDao
class DetailRepository(private val apiService: ApiService) {
    suspend fun getDetailMovie(movieId: Int) = apiService.getDetailMovie(movieId)
//    fun getFavoriteById(movieId: Int) = favoriteDao.readFavoriteById(movieId)
//    fun addToFavorite(favorite: Favorite) = favoriteDao.insertFavorite(favorite)
//    fun removeFromFavorite(favorite: Favorite) = favoriteDao.deleteFavorite(favorite)
}