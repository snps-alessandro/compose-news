package it.alexs.composenews.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import it.alexs.sharelibs.Screen
import it.alexs.sharelibs.theme.NewsTheme
import it.alexs.sharelibs.theme.typography
import it.alexs.sharelibs.utils.NewsToolbar

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
    val categoriesState by mainViewModel.categories.observeAsState(listOf())

    NewsMainScreenContent(
        categories = categoriesState,
        scaffoldState = scaffoldState,
        navController = navController
    )
}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalFoundationApi
@Composable
private fun NewsMainScreenContent(
    categories: List<CategoryNews>,
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

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
private fun LoadingContent(
    modifier: Modifier,
    categories: List<CategoryNews>,
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

        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(categories) { category ->
                CategoryItem(
                    category,
                    { navController.navigate(Screen.Article.createRoute(category.name)) })
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CategoryItem(
    category: CategoryNews,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(4.dp)
            .clickable { onClick() },
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = contentColorFor(backgroundColor = backgroundColor)
    ) {
        Row {
            Image(
                painter = painterResource(id = category.imageAssets),
                contentDescription = category.name,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )

            Text(
                text = category.name,
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
                    .padding(8.dp),
                style = typography.body1,
            )
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun MainScreenPreview() {
    NewsTheme {
        NewsMainScreenContent(
            categories = listOf(
                CategoryNews("Business", R.drawable.business),
                CategoryNews("Entertainment", R.drawable.entertainment),
                CategoryNews("General", R.drawable.general),
                CategoryNews("Health", R.drawable.health),
                CategoryNews("Science", R.drawable.science),
                CategoryNews("Sport", R.drawable.sport),
                CategoryNews("Technology", R.drawable.technology)
            ),
            scaffoldState = rememberScaffoldState(),
            navController = rememberNavController()
        )
    }
}
