package com.ajgroup.themoviedbnew.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajgroup.themoviedbnew.data.api.Resource
import com.ajgroup.themoviedbnew.data.api.model.MoviesResponse
import com.ajgroup.themoviedbnew.repository.HomeRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    private val _playingNow = MutableLiveData<Resource<MoviesResponse>>()
    val errorDiscovery: MutableLiveData<String> = MutableLiveData()
    val isLoadingDiscovery = MutableLiveData<Boolean>()
    val playingNow: LiveData<Resource<MoviesResponse>> get() = _playingNow
    private val _discoveryMovies: MutableLiveData<MoviesResponse> by lazy {
        MutableLiveData<MoviesResponse>().also {
            getDiscoveryMovies()
        }
    }
    fun getAllPlayingNow(){
        viewModelScope.launch {
            _playingNow.postValue(Resource.loading())
            try {
                _playingNow.postValue(Resource.success(repository.getAllPlayingNow()))
            }catch (exception : Exception){
                _playingNow.postValue(Resource.error(exception.message?: "Error Occurred"))
            }
        }
    }
//    val discoveryMovies: LiveData<MoviesResponse> = _discoveryMovies

    private fun getDiscoveryMovies() {
        isLoadingDiscovery.postValue(true)
        repository.getDiscoverMovies().enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                isLoadingDiscovery.postValue(false)
                if (response.code() == 200) {
                    _discoveryMovies.postValue(response.body())
                } else {
                    errorDiscovery.postValue("Error")
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                isLoadingDiscovery.postValue(false)
            }

        })
    }

//    val namaPreference = repository.getNama()
}