package com.example.damn_jmhg_examenll.api.entities

data class CommentEntity(
    val postId: Long,
    val id: Long,
    val name: String,
    val email: String,
    val body: String
)
