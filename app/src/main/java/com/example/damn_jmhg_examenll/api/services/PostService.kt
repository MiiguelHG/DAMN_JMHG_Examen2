package com.example.damn_jmhg_examenll.api.services

import com.example.damn_jmhg_examenll.api.entities.PostEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {
    @GET("posts")
    suspend fun getPostsByUserId(@Query("userId") userId: Long): List<PostEntity>
}