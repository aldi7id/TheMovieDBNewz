package com.ajgroup.themoviedbnew.di


import com.ajgroup.themoviedbnew.data.api.ApiService
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val BASE_URL = "https://api.themoviedb.org/3/"
val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request()
                val queryBuild = request.url
                    .newBuilder()
                    .addQueryParameter("api_key", "00dfa9ebae2c776e7509c85faa9a2e23").build()
                it.proceed(request.newBuilder().url(queryBuild).build())

            }
            .addInterceptor(
                ChuckerInterceptor.Builder(get())
                    .collector(ChuckerCollector(get()))
                    .maxContentLength(2500000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single {
        get<Retrofit>().create(ApiService::class.java)
    }
}