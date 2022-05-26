package com.ajgroup.themoviedbnew.data.local

import androidx.room.*
import com.ajgroup.themoviedbnew.data.local.model.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM Favorite")
    suspend fun readFavorites(): List<Favorite>

    @Query("SELECT * FROM Favorite WHERE id=:id")
    fun readFavoriteById(id: Int): Favorite?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: Favorite):Long

    @Delete
    fun deleteFavorite(favorite: Favorite):Int
}