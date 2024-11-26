package com.example.damn_jmhg_examenll.api.services

import com.example.damn_jmhg_examenll.api.entities.CommentEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface CommentService {
    @GET("comments")
    suspend fun getCommentsByPostId(@Query("postId") id: Long): List<CommentEntity>
}