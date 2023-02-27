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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.shaarawy.githubtrends.R
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
    Scaffold(topBar = {
        TopAppBar(backgroundColor = MaterialTheme.colors.background) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.trending),
                style = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.onSurface),
                textAlign = TextAlign.Center
            )
        }
    }) {
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
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.retry))
        LottieAnimation(
            modifier = Modifier.fillMaxWidth(),
            composition = composition,
            iterations = LottieConstants.IterateForever,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.retry_title),
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.retry_desc),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(64.dp))
        OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = onRetry) {
            Text(text = stringResource(R.string.retry))
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