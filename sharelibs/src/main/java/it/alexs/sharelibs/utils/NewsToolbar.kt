package it.alexs.sharelibs.utils

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

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