package com.ajgroup.themoviedbnew.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val releaseDate: String,
    val posterPath: String?,
    val title: String,
    val voteAverage: Double
)