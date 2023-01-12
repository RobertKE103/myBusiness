package com.example.mybusiness.domain

data class BusinessItem(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = 0
)
