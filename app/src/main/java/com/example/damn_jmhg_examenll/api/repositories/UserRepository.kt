package com.example.damn_jmhg_examenll.api.repositories

import com.example.damn_jmhg_examenll.api.network.ClienteRetrofit
import com.example.damn_jmhg_examenll.api.services.UserService

class UserRepository(val userService: UserService = ClienteRetrofit.getInstanceRetrofit) {
    suspend fun getAllUsers() = userService.getAllUsers()
    suspend fun getUserById(id: Long) = userService.getUserById(id)
}