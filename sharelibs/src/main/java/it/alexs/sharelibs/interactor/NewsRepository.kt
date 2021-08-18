package it.alexs.sharelibs.interactor

import it.alexs.sharelibs.model.WrapperArticle
import it.alexs.sharelibs.network.NewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApiService: NewsApiService
) {

    suspend fun topHeadlinesFromCategory(category: String): Flow<WrapperArticle> {
        val queryMap = mapOf(
            "category" to category,
            "country" to "it"
        )

        return flow {
            emit(newsApiService.topHeadlines(queryMap))
        }.flowOn(Dispatchers.IO)
    }
}