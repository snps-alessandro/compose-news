package it.alexs.article.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.alexs.sharelibs.interactor.NewsRepository
import it.alexs.sharelibs.model.WrapperArticle
import it.alexs.sharelibs.utils.state.StateUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {

    private val _articles = MutableStateFlow<StateUI<WrapperArticle>>(StateUI.loading())
    val articles: MutableStateFlow<StateUI<WrapperArticle>>
        get() = _articles

    suspend fun topHeadlinesFromCategory(category: String): Flow<WrapperArticle> {
        return newsRepository.topHeadlinesFromCategory(category = category)
    }
}