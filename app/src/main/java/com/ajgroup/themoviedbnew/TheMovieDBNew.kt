package com.ajgroup.themoviedbnew

import android.app.Application
import com.ajgroup.themoviedbnew.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TheMovieDBNew:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TheMovieDBNew)
            modules(networkModule, databaseModule, dataStoreModule, repositoryModule, viewModelModule)
        }
    }
}