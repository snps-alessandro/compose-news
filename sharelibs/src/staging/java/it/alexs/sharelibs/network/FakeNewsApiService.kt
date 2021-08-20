package it.alexs.sharelibs.network

import it.alexs.sharelibs.model.Article
import it.alexs.sharelibs.model.Source
import it.alexs.sharelibs.model.WrapperArticle

class FakeNewsApiService : NewsApiService {

    override suspend fun topHeadlines(query: Map<String, String>): WrapperArticle {
        return WrapperArticle()
    }

    fun getFakeArticles(): List<Article> {
        val articles = mutableListOf<Article>()
        repeat((0..30).count()) {
            articles.add(
                Article(
                    title = "Title $it",
                    author = "Author $it",
                    source = Source(
                        id = "$it",
                        name = "Source $it"
                    ),
                    content = "Content of author $it and source $it",
                    description = "Description of author $it and source $it",
                    urlToImage = "https://picsum.photos/600/800",
                    publishedAt = "2021-08-18T10:10:34Z"
                )
            )
        }

        return articles
    }
}