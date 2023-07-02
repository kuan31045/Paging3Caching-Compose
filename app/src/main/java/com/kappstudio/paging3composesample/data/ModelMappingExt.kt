package com.kappstudio.paging3composesample.data

import com.kappstudio.paging3composesample.data.local.RepoEntity
import com.kappstudio.paging3composesample.data.remote.RepoDto

fun RepoDto.toRepoEntity(): RepoEntity {
    return RepoEntity(
        repoId = id,
        name = name,
        description = description,
        starCount = stargazers_count
    )
}

fun RepoEntity.toRepo(): Repo {
    return Repo(
        id = repoId,
        name = name,
        description = description,
        starCount = starCount
    )
}