package com.example.damn_jmhg_examenll.api.network

import com.example.damn_jmhg_examenll.api.services.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClienteRetrofit {
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val getInstanceRetrofit: UserService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }
}