package com.kappstudio.paging3composesample.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val repoId: Int,
    val name: String,
    val description: String?,
    val starCount: Int
)