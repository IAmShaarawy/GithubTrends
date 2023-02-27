package dev.shaarawy.githubtrends.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.shaarawy.githubtrends.presentation.main.MainAction
import dev.shaarawy.githubtrends.presentation.main.MainState
import dev.shaarawy.githubtrends.presentation.main.MainViewModel
import dev.shaarawy.githubtrends.presentation.main.item.ItemAction
import dev.shaarawy.githubtrends.presentation.main.item.ItemViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    SideEffects(viewModel = viewModel)
    val state = viewModel.mainViewState
        .collectAsState()
        .value
    Scaffold {
        if (state is MainState.Empty)
            Empty()
        if (state is MainState.Content)
            Content(items = state.data)
        if (state is MainState.Error)
            Error(onRetry = { viewModel.onAction(MainAction.Retry) })
        if (state is MainState.Loading)
            Loading()
    }
}

@Composable
private fun SideEffects(viewModel: MainViewModel) {
    val uriHandler = LocalUriHandler.current
    LaunchedEffect(key1 = Unit) {
        viewModel.onItemAction
            .filterIsInstance<ItemAction.OpenWebLink>()
            .collectLatest { uriHandler.openUri(it.link) }
    }
}


@Composable
fun Empty() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "No data available",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Content(items: List<ItemViewModel>) {
    LazyColumn(contentPadding = PaddingValues(start = 16.dp)) {
        itemsIndexed(items) { index, item ->
            RepoItem(itemViewModel = item)
            if (index < items.lastIndex)
                Divider(
                    modifier = Modifier
                        .fillParentMaxWidth()
                )
        }
    }
}

@Composable
@Preview
fun Error(onRetry: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.Close,
            contentDescription = "logo",
            modifier = Modifier.size(128.dp),
            tint = MaterialTheme.colors.onPrimary
        )
        Text(
            text = "Something Went Wrong",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Center
        )
        TextButton(onClick = onRetry) {
            Text(text = "Retry")
        }
    }
}

@Composable
@Preview
fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}