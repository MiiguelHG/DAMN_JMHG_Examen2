package com.example.damn_jmhg_examenll.api.entities

data class UserEntity(
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,
    val address: Address,
    val company: Company,
)
