package com.lesteban.coincapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey
    val coin: String,
    val name: String
)
