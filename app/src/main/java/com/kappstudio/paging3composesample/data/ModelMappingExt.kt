package com.kappstudio.paging3composesample.data

import com.kappstudio.paging3composesample.data.local.RepoEntity
import com.kappstudio.paging3composesample.data.remote.RepoDto

fun RepoDto.toRepoEntity(): RepoEntity {
    return RepoEntity(
        id = id,
        name = name,
        description = description,
        starCount = stargazers_count
    )
}

fun RepoEntity.toRepo(): Repo {
    return Repo(
        id = id,
        name = name,
        description = description,
        starCount = starCount
    )
}