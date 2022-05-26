package com.ajgroup.themoviedbnew.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["email"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val name: String,
    val email: String,
    val password: String,
    val avatarPath: String=""
)