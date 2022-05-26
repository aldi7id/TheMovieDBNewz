package com.ajgroup.themoviedbnew.repository

import com.ajgroup.themoviedbnew.data.local.FavoriteDao

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    suspend fun getAllFavorites() = favoriteDao.readFavorites()
}