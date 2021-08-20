package it.alexs.composenews.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import it.alexs.composenews.R
import javax.inject.Inject

@HiltViewModel
class FakeMainViewModel @Inject constructor(): MainViewModel() {

    private val _categories = MutableLiveData<List<CategoryNews>>()
    override val categories: MutableLiveData<List<CategoryNews>>
        get() = _categories

    init {
        _categories.value = listOf(
            CategoryNews("Fake Business", R.drawable.business),
            CategoryNews("Fake Entertainment", R.drawable.entertainment),
            CategoryNews("Fake General", R.drawable.general),
            CategoryNews("Fake Health", R.drawable.health),
            CategoryNews("Fake Science", R.drawable.science),
            CategoryNews("Fake Sport", R.drawable.sport),
            CategoryNews("Fake Technology", R.drawable.technology)
        )
    }
}