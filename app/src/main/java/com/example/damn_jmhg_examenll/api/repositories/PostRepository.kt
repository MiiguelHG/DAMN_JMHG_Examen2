package com.example.damn_jmhg_examenll.api.repositories

import com.example.damn_jmhg_examenll.api.network.ClienteRetrofitPost
import com.example.damn_jmhg_examenll.api.services.PostService

class PostRepository(val postService: PostService = ClienteRetrofitPost.getInstanceRetrofit) {
    suspend fun getPostsByUserId(userId: Long) = postService.getPostsByUserId(userId)
}