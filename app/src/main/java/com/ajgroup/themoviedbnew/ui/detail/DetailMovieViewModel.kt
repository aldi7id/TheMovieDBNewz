package com.ajgroup.themoviedbnew.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajgroup.themoviedbnew.data.api.Resource
import com.ajgroup.themoviedbnew.data.api.model.DetailResponse
import com.ajgroup.themoviedbnew.data.local.model.Favorite
import com.ajgroup.themoviedbnew.repository.DetailRepository
import kotlinx.coroutines.launch

class DetailMovieViewModel(private val repository: DetailRepository) : ViewModel() {
    private val _detailMovie = MutableLiveData<Resource<DetailResponse>>()
    val detailMovie: LiveData<Resource<DetailResponse>> get() = _detailMovie

    fun getDetailMovie(movieId: Int) {
        viewModelScope.launch {
            _detailMovie.postValue(Resource.loading())
            try {
                _detailMovie.postValue(Resource.success(repository.getDetailMovie(movieId)))
            } catch (exp: Exception) {
                _detailMovie.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

    //favorite teritory
    private val _isFavoriteExist = MutableLiveData<Boolean>()
    val isFavoriteExist = _isFavoriteExist

    fun changeFavorite(state: Boolean) {
        _isFavoriteExist.postValue(state)
    }

    fun getFavoriteById(movieId: Int) = repository.getFavoriteById(movieId)
    fun addToFavorite(favorite: Favorite) = repository.addToFavorite(favorite)
    fun removeFromFavorite(favorite: Favorite) = repository.removeFromFavorite(favorite)
}