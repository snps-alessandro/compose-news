package it.alexs.composenews.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
): ViewModel() {

    private val _categories = MutableLiveData<List<String>>(listOf<String>(
        "Business",
        "Entertainment",
        "General",
        "Health",
        "Science",
        "Sport",
        "Technology"
    ))
    val categories: MutableLiveData<List<String>>
        get() = _categories
}