package com.example.mybusiness.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "business_item")

data class BusinessItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean,
)
