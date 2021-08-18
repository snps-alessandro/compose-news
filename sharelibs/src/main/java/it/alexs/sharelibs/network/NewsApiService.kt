package it.alexs.sharelibs.network

import it.alexs.sharelibs.model.WrapperArticle
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsApiService {

    @GET("top-headlines")
    suspend fun topHeadlines(
        @QueryMap query: Map<String, String>
    ): WrapperArticle
}