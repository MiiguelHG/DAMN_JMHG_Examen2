package com.example.damn_jmhg_examenll.room.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,
    @Embedded val address: Address,
    @Embedded val company: Company
)
