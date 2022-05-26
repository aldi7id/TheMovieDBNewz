package com.ajgroup.themoviedbnew.repository

import com.ajgroup.themoviedbnew.data.api.ApiService
import com.ajgroup.themoviedbnew.data.local.FavoriteDao
import com.ajgroup.themoviedbnew.data.local.model.Favorite

class DetailRepository(private val apiService: ApiService, private val favoriteDao: FavoriteDao) {
    suspend fun getDetailMovie(movieId: Int) = apiService.getDetailMovie(movieId)
    fun getFavoriteById(movieId: Int) = favoriteDao.readFavoriteById(movieId)
    fun addToFavorite(favorite: Favorite) = favoriteDao.insertFavorite(favorite)
    fun removeFromFavorite(favorite: Favorite) = favoriteDao.deleteFavorite(favorite)
}