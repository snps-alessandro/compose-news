package it.alexs.article.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import it.alexs.article.R
import it.alexs.sharelibs.model.Article
import it.alexs.sharelibs.model.Source
import it.alexs.sharelibs.model.WrapperArticle
import it.alexs.sharelibs.theme.NewsTheme
import it.alexs.sharelibs.theme.typography
import it.alexs.sharelibs.utils.NewsToolbar
import it.alexs.sharelibs.utils.orPlaceholder
import it.alexs.sharelibs.utils.state.UiState
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@Composable
fun ArticleScreen(
    navController: NavController,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    articleViewModel: ArticleViewModel = hiltViewModel(),
    category: String
) {

    val articles by articleViewModel.articles.observeAsState(UiState(loading = true))

    val filter by articleViewModel.filter.observeAsState(initial = "")

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
                filter = filter,
                onFilterChange = { articleViewModel.onTextChange(it) },
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
    filter: String,
    onFilterChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState,
    onRefresh: () -> Unit
) {

    if (articles.isFailure) {
        ErrorContent(scaffoldState = scaffoldState)
    }

    LoadingContent(
        empty = articles.initialLoading,
        emptyContent = { FullScreenLoadingContent(modifier = modifier) },
        loading = articles.loading,
        onRefresh = onRefresh,
        filter = filter,
        onFilterChange = onFilterChange,
        content = {
            ArticleView(
                articles = articles,
                modifier = modifier,
                placeHolder = articles.loading,
                onRefresh = onRefresh
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
    filter: String,
    onFilterChange: (String) -> Unit,
    content: @Composable () -> Unit
) {

    if (empty) {
        emptyContent()
    } else {
        Column {
            OutlinedTextField(
                value = filter,
                onValueChange = onFilterChange,
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface),
                placeholder = { Text(text = "Cerca...") }
            )

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = loading),
                onRefresh = onRefresh,
                content = content
            )
        }
    }
}

@Composable
fun ArticleView(
    articles: UiState<WrapperArticle>,
    modifier: Modifier,
    placeHolder: Boolean,
    onRefresh: () -> Unit
) {
    val articleList = articles.data?.articles ?: listOf()

    if (articles.data != null) {
        ArticleList(
            modifier = modifier,
            articles = articleList,
            placeholder = placeHolder
        )
    } else if (!articles.isFailure) {
        TextButton(onClick = onRefresh, modifier = modifier.fillMaxSize()) {
            Text(text = "Tap to load content", textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun ArticleList(
    articles: List<Article>,
    placeholder: Boolean,
    modifier: Modifier
) {
    LazyColumn(
        state = rememberLazyListState(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(articles) { article ->
            CardArticle(
                article = article,
                modifier = modifier,
                placeholder = modifier.placeholder(
                    visible = placeholder,
                    highlight = PlaceholderHighlight.shimmer()
                )
            )
        }
    }
}

@Composable
fun CardArticle(article: Article, modifier: Modifier, placeholder: Modifier) {
    Card(elevation = 4.dp) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = article.urlToImage,
                    builder = {
                        transformations(RoundedCornersTransformation(8f))
                        crossfade(true)
                        placeholder(R.drawable.ic_baseline_photo_24)
                        scale(Scale.FILL)
                    },
                ),
                contentScale = ContentScale.Crop,
                contentDescription = "Image of ${article.title}",
                modifier = placeholder
                    .padding(bottom = 4.dp)
                    .size(width = 330.dp, height = 124.dp)
            )
            Text(
                text = article.title.orPlaceholder(),
                style = typography.h3,
                modifier = placeholder
            )
            Spacer(modifier = modifier.padding(1.dp))
            Text(
                text = article.description.orPlaceholder(),
                style = typography.overline,
                modifier = placeholder
            )
            Spacer(modifier = modifier.padding(1.dp))
            Text(
                text = DateTime.parse(article.publishedAt)
                    .toString(DateTimeFormat.forPattern("dd/MM/YYYY HH:mm")),
                style = typography.overline,
                modifier = placeholder
            )
            Spacer(modifier = modifier.padding(8.dp))
            Text(
                text = article.source?.name.orPlaceholder("Source"),
                modifier = placeholder
            )
            Spacer(modifier = modifier.padding(2.dp))
            Text(
                text = article.author.orPlaceholder("Author"),
                modifier = placeholder
            )
            Spacer(modifier = modifier.padding(8.dp))
            Text(
                text = article.content.orPlaceholder(),
                style = typography.body1,
                modifier = placeholder,
                overflow = TextOverflow.Ellipsis,
                maxLines = 4
            )
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
    scaffoldState: ScaffoldState
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
                ArticleList(
                    articles = listOf(
                        Article(
                            source = Source(name = "CNN"),
                            title = "Jannik Sinner best player of 2021",
                            urlToImage = "https://net-storage.tcccdn.com/storage/tuttojuve.com/img_notizie/thumb3/c8/c8ea7e78ccb06f501010c6db5313533b-52248-8087c39faad72121becb6d1778778c8e.jpeg",
                            publishedAt = "2021-08-18T10:10:34Z",
                            content = "Test content",
                            description = "Test description"
                        ),
                        Article(
                            source = Source(name = "CNN"),
                            author = "Bob",
                            title = "Jannik Sinner best player of 2021",
                            urlToImage = "https://net-storage.tcccdn.com/storage/tuttojuve.com/img_notizie/thumb3/c8/c8ea7e78ccb06f501010c6db5313533b-52248-8087c39faad72121becb6d1778778c8e.jpeg",
                            publishedAt = "2021-08-18T10:10:34Z"
                        )
                    ),
                    modifier = Modifier.padding(innerPadding),
                    placeholder = false
                )
            })

    }
}

@Preview
@Composable
fun TextFieldPreview() {
    NewsTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text(text = "TextField") }) },
            content = { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    Spacer(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.Gray)
                    )
                    OutlinedTextField(
                        value = "ciao",
                        onValueChange = { },
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(MaterialTheme.colors.surface)
                            .padding(12.dp)
                            .fillMaxWidth()
                    )
                }
            }
        )

    }
}