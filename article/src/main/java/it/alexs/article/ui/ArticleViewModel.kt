package it.alexs.article.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.alexs.sharelibs.interactor.NewsRepository
import it.alexs.sharelibs.model.WrapperArticle
import it.alexs.sharelibs.utils.state.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {

    private val _articles = MutableStateFlow<State<WrapperArticle>>(State.loading())
    val articles: MutableStateFlow<State<WrapperArticle>>
        get() = _articles

    fun topHeadlinesFromCategory(category: String) {
        viewModelScope.launch {
            newsRepository.topHeadlinesFromCategory(category = category.lowercase())
                .catch { e -> _articles.value = State.failure(e) }
                .onStart { _articles.value = State.loading() }
                .collectLatest {
                    _articles.value = State.success(it)
                }
        }
    }
}