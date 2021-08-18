package it.alexs.article.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import it.alexs.composenews.ui.utils.NewsToolbar
import it.alexs.sharelibs.model.Article
import it.alexs.sharelibs.model.Source
import it.alexs.sharelibs.model.WrapperArticle
import it.alexs.sharelibs.theme.NewsTheme
import it.alexs.sharelibs.theme.typography
import it.alexs.sharelibs.utils.state.State

@Composable
fun ArticleScreen(
    navController: NavController,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    articleViewModel: ArticleViewModel = hiltViewModel(),
    category: String
) {

    val articles = articleViewModel.articles.collectAsState()

    articleViewModel.topHeadlinesFromCategory(category)

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
                articles = articles.value,
                modifier = Modifier.padding(innerPadding),
                scaffoldState = scaffoldState
            )
        }
    )
}

@Composable
fun ArticleContent(
    articles: State<WrapperArticle>,
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState
) {

    when {
        articles.isFailure -> {
            ErrorContent(exception = articles.exceptionOrNull(), scaffoldState = scaffoldState)
        }
        articles.isLoading -> {
            LoadingContent(modifier = modifier)
        }
        articles.isSuccess -> {
            ArticleView(articles = articles.getOrNull(), modifier = modifier)
        }
    }
}

@Composable
fun ArticleView(articles: WrapperArticle?, modifier: Modifier) {
    val articleToShow = articles?.articles ?: emptyList()

    LazyColumn {
        items(articleToShow) { article ->
            CardArticle(article = article, modifier = modifier)
        }
    }
}

@Composable
fun CardArticle(article: Article, modifier: Modifier) {
    Column(modifier.padding(8.dp)) {
        Text(text = "Soruce: ${article.source?.name ?: "unknow"}")
        Text(text = "Author: ${article.author ?: "unknow"}")
        Divider()
        Text(text = article.title ?: "No Title", style = typography.h3)
        Text(text = article.description ?: "No description", style = typography.overline)
        Text(
            text = article.content ?: "No content",
            style = typography.body1,
            modifier = modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun LoadingContent(modifier: Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator()
        Spacer(modifier = modifier.requiredHeight(8.dp))
    }
}

@Composable
fun ErrorContent(exception: Throwable?, scaffoldState: ScaffoldState) {
    LaunchedEffect(scaffoldState) {
        scaffoldState.snackbarHostState.showSnackbar(
            exception.toString()
        )
    }
}

@Preview
@Composable
fun ArticlePreview() {
    NewsTheme {
        CardArticle(
            article = Article(
                source = Source(name = "CNN"),
                author = "Bob",
                title = "Jannik Sinner best player of 2021"
            ), modifier = Modifier
        )
    }
}