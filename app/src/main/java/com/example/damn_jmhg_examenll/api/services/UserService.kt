package com.example.damn_jmhg_examenll.api.services

import com.example.damn_jmhg_examenll.api.entities.UserEntity
import retrofit2.http.GET

interface UserService {
    @GET("users")
    suspend fun getAllUsers(): List<UserEntity>

    @GET("users/{id}")
    suspend fun getUserById(id: Long): UserEntity
}