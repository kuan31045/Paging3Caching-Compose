package com.kappstudio.paging3composesample.data.remote

import retrofit2.http.GET
import retrofit2.http.Query


interface GithubApi {

    @GET("search/repositories?sort=stars&q=Android")
    suspend fun searchRepos(
        @Query("page") page: Int, @Query("per_page") perPage: Int
    ): RepoResponse

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}