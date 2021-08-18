package it.alexs.composenews.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import it.alexs.article.ui.ArticleScreen
import it.alexs.composenews.R
import it.alexs.sharelibs.theme.NewsTheme
import it.alexs.composenews.ui.utils.NewsToolbar
import it.alexs.sharelibs.Screen

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NewsTheme {

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.id.route
                ) {
                    composable(route = Screen.Home.id.route) {
                        NewsMainScreen(navController)
                    }

                    composable(route = Screen.Article.id.route) { navBackStackEntry ->
                        val category = navBackStackEntry.arguments?.getString("category")

                        requireNotNull(category) { "category parameter wasn't found." }

                        ArticleScreen(navController = navController, category = category)
                    }
                }

            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun NewsMainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = viewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val categoriesState = mainViewModel.categories.observeAsState(listOf())

    NewsMainScreenContent(
        categories = categoriesState.value,
        scaffoldState = scaffoldState,
        navController = navController
    )
}

@ExperimentalFoundationApi
@Composable
private fun NewsMainScreenContent(
    categories: List<String>,
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState,
    navController: NavController
) {

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { NewsToolbar(title = stringResource(id = R.string.app_name)) },
        content = { innerPadding ->
            LoadingContent(
                modifier.padding(innerPadding),
                categories,
                navController
            )
        }
    )
}

@ExperimentalFoundationApi
@Composable
private fun LoadingContent(
    modifier: Modifier,
    categories: List<String>,
    navController: NavController
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.app_claim),
            modifier = modifier
                .padding(bottom = 20.dp)
        )

        Text(
            text = "Below you can choose the types of news that interest you.",
            modifier = modifier
                .padding(bottom = 12.dp)
        )

        val listState = rememberLazyListState()

        LazyVerticalGrid(
            state = listState,
            cells = GridCells.Adaptive(128.dp)
        ) {
            items(categories) { category ->
                CategoryItem(category, { navController.navigate(Screen.Article.createRoute(category)) })
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: String,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = action,
        modifier = modifier.padding(4.dp)
    ) {
        Text(text = category)
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun MainScreenPreview() {
    NewsTheme {
        NewsMainScreenContent(
            categories = listOf("Politica", "Sport", "Natura"),
            scaffoldState = rememberScaffoldState(),
            navController = rememberNavController()
        )
    }
}
