package com.ajgroup.themoviedbnew.di

import com.ajgroup.themoviedbnew.repository.DetailRepository
import com.ajgroup.themoviedbnew.repository.FavoriteRepository
import com.ajgroup.themoviedbnew.repository.HomeRepository
import com.ajgroup.themoviedbnew.repository.VerifRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::VerifRepository)
    singleOf(::HomeRepository)
    singleOf(::FavoriteRepository)
    singleOf(::DetailRepository)
}