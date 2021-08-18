package it.alexs.sharelibs.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = blue700,
    primaryVariant = blue100,
    secondary = black900,
    secondaryVariant = black600,
    onPrimary = black,
    onSecondary = white,
    surface = white,
    onSurface = black,
    onBackground = black,
    background = white,
    error = red800,
    onError = white
)

private val LightColorPalette = lightColors(
    primary = blue400,
    primaryVariant = blue100,
    secondary = black800,
    secondaryVariant = black600,
    surface = white,
    onSurface = black,
    onBackground = black,
    background = white,
    error = red800,
    onError = white
)

@Composable
fun NewsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
