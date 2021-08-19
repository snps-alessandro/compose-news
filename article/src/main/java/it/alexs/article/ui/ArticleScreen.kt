package it.alexs.article.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import it.alexs.sharelibs.model.Article
import it.alexs.sharelibs.model.Source
import it.alexs.sharelibs.model.WrapperArticle
import it.alexs.sharelibs.theme.NewsTheme
import it.alexs.sharelibs.theme.typography
import it.alexs.sharelibs.utils.NewsToolbar
import it.alexs.sharelibs.utils.state.UiState

@Composable
fun ArticleScreen(
    navController: NavController,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    articleViewModel: ArticleViewModel = hiltViewModel(),
    category: String
) {

    val articles by articleViewModel.articles.observeAsState(UiState(loading = true))


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            NewsToolbar(
                title = category,
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ChevronLeft, contentDescription = null)
                    }
                }
            )
        },
        content = { innerPadding ->
            ArticleContent(
                articles = articles,
                modifier = Modifier.padding(innerPadding),
                scaffoldState = scaffoldState,
                onRefresh = { articleViewModel.topHeadlinesFromCategory(category = category) }
            )
        }
    )
}

@Composable
fun ArticleContent(
    articles: UiState<WrapperArticle>,
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState,
    onRefresh: () -> Unit
) {

    if (articles.isFailure) {
        ErrorContent(scaffoldState = scaffoldState, onRefresh = onRefresh)
    }

    LoadingContent(
        empty = articles.initialLoading,
        emptyContent = { FullScreenLoadingContent(modifier = modifier) },
        loading = articles.loading,
        onRefresh = onRefresh,
        content = {
            ArticleView(
                articles = articles,
                modifier = modifier,
                onRefresh = onRefresh,
                placeHolder = articles.loading
            )
        }
    )
}

@Composable
fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {

    if (empty) {
        emptyContent()
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = loading),
            onRefresh = onRefresh,
            content = content
        )
    }
}

@Composable
fun ArticleView(
    articles: UiState<WrapperArticle>,
    modifier: Modifier,
    placeHolder: Boolean,
    onRefresh: () -> Unit
) {
    if (articles.data != null) {
        NewsList(modifier = modifier, articles = articles, placeHolder = placeHolder)
    } else if (!articles.isFailure) {
        TextButton(onClick = onRefresh, modifier = modifier.fillMaxSize()) {
            Text(text = "Tap to load content", textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun NewsList(
    articles: UiState<WrapperArticle>,
    placeHolder: Boolean,
    modifier: Modifier
) {
    LazyColumn {
        items(articles.data?.articles ?: listOf()) { article ->
            CardArticle(
                article = article,
                modifier = modifier,
                placeholder = modifier.placeholder(
                    visible = placeHolder,
                    highlight = PlaceholderHighlight.shimmer()
                )
            )
        }
    }
}

@Composable
fun CardArticle(article: Article, modifier: Modifier, placeholder: Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Card(elevation = 4.dp, modifier = modifier.padding(8.dp)) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = article.title ?: "No Title",
                    style = typography.h2,
                    modifier = placeholder
                )
                Text(
                    text = article.description ?: "No description",
                    style = typography.overline,
                    modifier = placeholder
                )
                Spacer(modifier = modifier.padding(8.dp))
                Text(
                    text = "Soruce: ${article.source?.name ?: "unknow"}",
                    modifier = placeholder
                )
                Spacer(modifier = modifier.padding(4.dp))
                Text(
                    text = "Author: ${article.author ?: "unknow"}",
                    modifier = placeholder
                )
                Spacer(modifier = modifier.padding(8.dp))
                Text(
                    text = article.content ?: "No content",
                    style = typography.body1,
                    modifier = placeholder
                )
            }
        }
    }
}

@Composable
fun FullScreenLoadingContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorContent(
    scaffoldState: ScaffoldState,
    onRefresh: () -> Unit
) {
    LaunchedEffect(scaffoldState) {
        val snackResult = scaffoldState.snackbarHostState.showSnackbar(
            message = "Can't update latest news"
        )
    }
}

@Preview
@Composable
fun ArticlePreview() {
    NewsTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text(text = "News") }) },
            content = { innerPadding ->
                CardArticle(
                    article = Article(
                        source = Source(name = "CNN"),
                        author = "Bob",
                        title = "Jannik Sinner best player of 2021"
                    ),
                    modifier = Modifier.padding(innerPadding),
                    placeholder = Modifier.placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer()
                    )
                )
            })

    }
}