package it.alexs.composenews.ui

import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import it.alexs.article.ui.ArticleContent
import it.alexs.sharelibs.model.WrapperArticle
import it.alexs.sharelibs.network.FakeNewsApiService
import it.alexs.sharelibs.theme.NewsTheme
import it.alexs.sharelibs.utils.state.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ArticleContentTest {


    @get:Rule(order = 2)
    val composeTestRule = createComposeRule()

    private val wrapperArticle = WrapperArticle(
        status = "ok",
        totalResults = 30,
        articles = FakeNewsApiService().getFakeArticles()
    )

    @Test
    fun shouldShowProgressBarInArticleScreen() {
        initCompose(UiState(loading = true))
        composeTestRule
            .onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
            .assertIsDisplayed()
    }

    @Test
    fun shouldShowCercaEditText() {
        initCompose(UiState(data = wrapperArticle))
        composeTestRule
            .onNodeWithText("Cerca...")
            .assertIsDisplayed()
    }

    @Test
    fun shouldShowCard() {
        initCompose(UiState(data = wrapperArticle))
        composeTestRule
            .onNodeWithText("Title 0")
            .assertIsDisplayed()

        composeTestRule
            .onAllNodes(hasText("Description", substring = true))
            .onFirst()
            .assertIsDisplayed()

        composeTestRule
            .onAllNodes(hasText("Author", substring = true))
            .onFirst()
            .assertIsDisplayed()

        composeTestRule
            .onAllNodes(hasText("Source", substring = true))
            .onFirst()
            .assertIsDisplayed()

        composeTestRule
            .onAllNodes(hasText("Content", substring = true))
            .onFirst()
            .assertIsDisplayed()

        composeTestRule
            .onAllNodes(hasContentDescription("Image of", substring = true))
            .onFirst()
            .assertIsDisplayed()

    }



    private fun initCompose(state: UiState<WrapperArticle>) {
        composeTestRule.setContent {
            NewsTheme {
                ArticleContent(
                    articles = state,
                    filter = "",
                    onFilterChange = {},
                    scaffoldState = rememberScaffoldState()
                ) {

                }
            }
        }
    }
}