package it.alexs.composenews.ui.utils

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NewsToolbar(
    title: String,
    navigationIcon: @Composable (() -> Unit)? = null
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = navigationIcon
    )
}