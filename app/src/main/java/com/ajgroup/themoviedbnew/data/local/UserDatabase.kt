package com.ajgroup.themoviedbnew.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ajgroup.themoviedbnew.data.local.model.Favorite
import com.ajgroup.themoviedbnew.data.local.model.User

@Database(entities = [User::class, Favorite::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favoriteDao(): FavoriteDao
}