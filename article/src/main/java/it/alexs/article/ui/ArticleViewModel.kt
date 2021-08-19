package it.alexs.article.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.alexs.sharelibs.interactor.NewsRepository
import it.alexs.sharelibs.model.Article
import it.alexs.sharelibs.model.WrapperArticle
import it.alexs.sharelibs.utils.state.UiState
import it.alexs.sharelibs.utils.state.copyWithException
import it.alexs.sharelibs.utils.state.copyWithResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _articles = MutableLiveData(UiState<WrapperArticle>(loading = true))
    val articles: MutableLiveData<UiState<WrapperArticle>>
        get() = _articles

    private val _newsList = mutableListOf<Article>()

    private val _filter = MutableLiveData<String>("")
    val filter: MutableLiveData<String>
        get() = _filter

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
                .onCompletion { onTextChange(_filter.value ?: "") }
                .collectLatest {
                    _newsList.clear()
                    _newsList.addAll(it.articles ?: emptyList())
                    _articles.value = _articles.value?.copyWithResult(it)
                }
        }
    }

    fun onTextChange(newValue: String) {
        _filter.value = newValue
        viewModelScope.launch {
            flow {
                emit(_newsList.filter {
                    it.source?.name.orEmpty().lowercase().contains(newValue.lowercase())
                        || it.author.orEmpty().lowercase().contains(newValue.lowercase())
                })
            }.onStart { _articles.value = _articles.value?.copy(loading = true) }
                .catch { e -> _articles.value = _articles.value?.copyWithException(e) }
                .collectLatest {
                    _articles.value = _articles.value?.copyWithResult(WrapperArticle(articles = it))
                }
        }
    }
}