package com.kappstudio.paging3composesample.data.remote

data class RepoDto(
    val id: Int,
    val name: String,
    val description: String?,
    val stargazers_count: Int
)