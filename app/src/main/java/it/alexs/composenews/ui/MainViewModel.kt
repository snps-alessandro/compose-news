package it.alexs.composenews.ui

import androidx.annotation.DrawableRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import it.alexs.composenews.R
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(
) : ViewModel() {

    private val _categories = MutableLiveData<List<CategoryNews>>()
    open val categories: MutableLiveData<List<CategoryNews>>
        get() = _categories

    init {
        _categories.value = listOf(
            CategoryNews("Business", R.drawable.business),
            CategoryNews("Entertainment", R.drawable.entertainment),
            CategoryNews("General", R.drawable.general),
            CategoryNews("Health", R.drawable.health),
            CategoryNews("Science", R.drawable.science),
            CategoryNews("Sport", R.drawable.sport),
            CategoryNews("Technology", R.drawable.technology)
        )
    }
}

data class CategoryNews(
    val name: String,
    @DrawableRes val imageAssets: Int
)