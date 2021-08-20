package it.alexs.composenews.ui

import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import it.alexs.article.ui.ArticleScreen
import it.alexs.article.ui.ArticleViewModel
import it.alexs.sharelibs.Screen
import it.alexs.sharelibs.theme.NewsTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 1)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalFoundationApi
    @Before
    fun setUp() {
        hiltTestRule.inject()
        composeTestRule.setContent {
            NewsTheme {
                NewsMainScreen(
                    navigateToArticle = { Screen.Article.createRoute(it) },
                    mainViewModel = composeTestRule.activity.viewModels<FakeMainViewModel>().value
                )
            }
        }
    }

    @Test
    fun shouldShowNewsTextInToolbar() {
        composeTestRule.onNodeWithText("News").assertIsDisplayed()
    }

    @Test
    fun shouldThatFakeButtonsForCategoriesOfNewsAreClickable() {
        composeTestRule
            .onAllNodes(hasText("Fake", true))
            .assertAll(hasClickAction())

        composeTestRule
            .onAllNodes(hasText("Fake", true))
            .onFirst()
            .performClick()
    }
}
