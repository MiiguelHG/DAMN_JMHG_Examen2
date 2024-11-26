package com.example.damn_jmhg_examenll.api.repositories

import com.example.damn_jmhg_examenll.api.network.ClienteRetrofitComment
import com.example.damn_jmhg_examenll.api.services.CommentService

class CommentRepository(val commentService: CommentService = ClienteRetrofitComment.getInstanceRetrofit) {
    suspend fun getCommentsByPostId(postId: Long) = commentService.getCommentsByPostId(postId)
}