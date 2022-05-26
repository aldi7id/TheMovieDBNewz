package com.ajgroup.themoviedbnew.data.api

import com.ajgroup.themoviedbnew.data.api.model.DetailResponse
import com.ajgroup.themoviedbnew.data.api.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("tv/popular")
    suspend fun getPopularTv(): MoviesResponse

    @GET("movie/now_playing")
    suspend fun getAiringMovie(): MoviesResponse

    @GET("discover/movie")
    fun getDiscoverMovie(): Call<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovie(): MoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(): MoviesResponse

    @GET("movie/{movieId}")
    suspend fun getDetailMovie(@Path("movieId") movieId: Int): DetailResponse




}
