package it.alexs.article.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.alexs.sharelibs.interactor.NewsRepository
import it.alexs.sharelibs.model.WrapperArticle
import it.alexs.sharelibs.utils.state.UiState
import it.alexs.sharelibs.utils.state.copyWithException
import it.alexs.sharelibs.utils.state.copyWithResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _articles = MutableLiveData<UiState<WrapperArticle>>(UiState(loading = true))
    val articles: MutableLiveData<UiState<WrapperArticle>>
        get() = _articles

    init {
        savedStateHandle.get<String>("category")?.let {
            topHeadlinesFromCategory(it)
        }
    }

    fun topHeadlinesFromCategory(category: String) {
        viewModelScope.launch {
            newsRepository.topHeadlinesFromCategory(category = category)
                .onStart { _articles.value = _articles.value?.copy(loading = true) }
                .catch { e -> _articles.value = _articles.value?.copyWithException(e) }
                .collectLatest { _articles.value = _articles.value?.copyWithResult(it) }
        }
    }
}