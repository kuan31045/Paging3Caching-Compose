package com.kappstudio.paging3composesample.di

import android.content.Context
import androidx.room.Room
import com.kappstudio.paging3composesample.data.local.RepoDatabase
import com.kappstudio.paging3composesample.data.remote.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGithubApi(): GithubApi {
        return Retrofit.Builder()
            .baseUrl(GithubApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideRepoDatabase(@ApplicationContext context: Context): RepoDatabase {
        return Room.databaseBuilder(
            context,
            RepoDatabase::class.java,
            "repo.db"
        ).build()
    }
}