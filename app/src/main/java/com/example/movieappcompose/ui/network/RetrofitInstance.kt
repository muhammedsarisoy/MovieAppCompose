package com.example.movieappcompose.ui.network

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder().addInterceptor { chain ->
                val original = chain.request()
                val originalUrl = original.url
                val newUrl = originalUrl.newBuilder()
                    .addQueryParameter("api_key", "2766d672f73a91abc8f8a7214a9f64f7")
                    .build()

                val requestBuilder = original.newBuilder().url(newUrl)
                val request = requestBuilder.build()
                chain.proceed(request)
            }.build()
        )
        .build()

    val networkService: NetworkService = retrofit.create(NetworkService::class.java)
}