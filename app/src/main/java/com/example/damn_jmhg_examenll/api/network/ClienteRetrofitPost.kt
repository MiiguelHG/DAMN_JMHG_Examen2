package com.example.damn_jmhg_examenll.api.network

import com.example.damn_jmhg_examenll.api.services.PostService
import com.example.damn_jmhg_examenll.api.services.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClienteRetrofitPost {
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val getInstanceRetrofit: PostService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostService::class.java)
    }
}