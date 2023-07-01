package com.kappstudio.paging3composesample.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface RepoDao {

    @Upsert
    suspend fun upsertAll(repos: List<RepoEntity>)

    @Query("SELECT * FROM repoentity")
    fun pagingSource(): PagingSource<Int, RepoEntity>

    @Query("DELETE FROM repoentity")
    suspend fun clearAll()
}