package com.example.damn_jmhg_examenll.api.entities

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geo
)
