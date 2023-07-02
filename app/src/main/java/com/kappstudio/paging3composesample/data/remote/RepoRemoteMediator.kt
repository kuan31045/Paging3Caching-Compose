package com.kappstudio.paging3composesample.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingState
import com.kappstudio.paging3composesample.data.local.RepoDatabase
import com.kappstudio.paging3composesample.data.local.RepoEntity
import androidx.paging.LoadType
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kappstudio.paging3composesample.data.toRepoEntity
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

/*
 * Google documentation:
 * https://developer.android.com/reference/kotlin/androidx/paging/RemoteMediator
 */

@OptIn(ExperimentalPagingApi::class)
class RepoRemoteMediator(
    private val repoDb: RepoDatabase,
    private val githubApi: GithubApi
) : RemoteMediator<Int, RepoEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepoEntity>
    ): MediatorResult {
        return try {
            // Next page to load
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            delay(2000L)
            val repos = githubApi.searchRepos(
                page = loadKey,
                perPage = state.config.pageSize
            ).items

            repoDb.apply {
                withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        dao.clearAll()
                    }
                    val repoEntities = repos.map { it.toRepoEntity() }
                    dao.upsertAll(repoEntities)
                }
            }

            MediatorResult.Success(
                endOfPaginationReached = repos.size < state.config.pageSize
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}