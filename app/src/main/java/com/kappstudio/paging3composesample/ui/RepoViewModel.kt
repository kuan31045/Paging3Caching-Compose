package com.kappstudio.paging3composesample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.kappstudio.paging3composesample.data.Repository
import com.kappstudio.paging3composesample.data.toRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {
    val repoPagingFlow = repository.getRepoPager().map { pagingData ->
        pagingData.map { it.toRepo() }
    }.cachedIn(viewModelScope)
}