package com.ajgroup.themoviedbnew.di

import com.ajgroup.themoviedbnew.ui.detail.DetailMovieViewModel
import com.ajgroup.themoviedbnew.ui.favorite.FavoriteViewModel
import com.ajgroup.themoviedbnew.ui.home.HomeViewModel
import com.ajgroup.themoviedbnew.ui.verif.VerifViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::VerifViewModel)
    viewModelOf(::DetailMovieViewModel)
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::HomeViewModel)
}