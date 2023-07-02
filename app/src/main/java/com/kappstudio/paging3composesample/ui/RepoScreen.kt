package com.kappstudio.paging3composesample.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.kappstudio.paging3composesample.R
import com.kappstudio.paging3composesample.data.Repo
import com.kappstudio.paging3composesample.ui.theme.Paging3ComposeTheme

@Composable
fun RepoScreen(
    repos: LazyPagingItems<Repo>
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = repos.loadState) {
        if (repos.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (repos.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (repos.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(repos) { repo ->
                    if (repo != null) {
                        RepoItem(repo = repo, modifier = Modifier.fillMaxWidth())
                    }
                }
                item {
                    if (repos.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun RepoItem(repo: Repo, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        {
            Text(
                text = repo.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = repo.description ?: "no description")
            Row(modifier = Modifier.align(Alignment.End)) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_star_24),
                    contentDescription = "star"
                )
                Text(text = repo.starCount.toString())
            }
        }
    }
}

@Preview
@Composable
fun RepoItemPreview() {
    Paging3ComposeTheme {
        RepoItem(
            repo = Repo(
                id = 1,
                name = "SampleProject",
                description = "This is a description for sample project",
                starCount = 1000
            )
        )
    }
}