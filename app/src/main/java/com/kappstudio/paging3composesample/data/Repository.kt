package com.kappstudio.paging3composesample.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kappstudio.paging3composesample.data.local.RepoDatabase
import com.kappstudio.paging3composesample.data.local.RepoEntity
import com.kappstudio.paging3composesample.data.remote.GithubApi
import com.kappstudio.paging3composesample.data.remote.RepoRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface Repository {
    fun getRepoPager(): Flow<PagingData<RepoEntity>>
}

class RepositoryImpl @Inject constructor(
    private val githubApi: GithubApi,
    private val repoDb: RepoDatabase
) : Repository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getRepoPager(): Flow<PagingData<RepoEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = RepoRemoteMediator(
                githubApi = githubApi,
                repoDb = repoDb
            ),
            pagingSourceFactory = { repoDb.dao.pagingSource() }
        ).flow
    }
}