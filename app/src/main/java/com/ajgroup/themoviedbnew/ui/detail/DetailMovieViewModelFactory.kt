package com.ajgroup.themoviedbnew.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ajgroup.themoviedbnew.repository.DetailRepository

class DetailMovieViewModelFactory(private val repository: DetailRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailMovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailMovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}