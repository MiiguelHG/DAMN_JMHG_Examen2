package com.example.damn_jmhg_examenll.api.network

import com.example.damn_jmhg_examenll.api.services.CommentService
import com.example.damn_jmhg_examenll.api.services.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClienteRetrofitComment {
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val getInstanceRetrofit: CommentService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommentService::class.java)
    }
}