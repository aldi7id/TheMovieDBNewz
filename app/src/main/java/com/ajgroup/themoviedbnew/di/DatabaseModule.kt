package com.ajgroup.themoviedbnew.di

import androidx.room.Room
import com.ajgroup.themoviedbnew.data.local.UserDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(get(),
        UserDatabase::class.java, "userdatabase.db").build()
    }
    single {
        get<UserDatabase>().userDao()
    }
    single {
        get<UserDatabase>().favoriteDao()
    }
}