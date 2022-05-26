package com.ajgroup.themoviedbnew.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajgroup.themoviedbnew.data.local.model.Favorite
import com.ajgroup.themoviedbnew.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteRepository): ViewModel() {
    private val _allFavorites: MutableLiveData<List<Favorite?>> = MutableLiveData()
    val allFavorites: LiveData<List<Favorite?>> = _allFavorites
    fun getAllFavorites(){
        viewModelScope.launch {
            val allFavorites = repository.getAllFavorites()
            _allFavorites.postValue(allFavorites)
        }
    }

}