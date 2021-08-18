package it.alexs.sharelibs


enum class ScreenName(val route: String) {
    HOME("home"),
    ARTICLE("article/{category}")
}

sealed class Screen(val id: ScreenName) {
    object Home : Screen(ScreenName.HOME)
    object Article: Screen(ScreenName.ARTICLE) {
        fun createRoute(category: String) = "article/$category"
    }
}