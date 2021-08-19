package it.alexs.sharelibs.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = primaryDark,
    primaryVariant = primaryVariant,
    secondary = secondaryDark,
    secondaryVariant = secondaryVariant,
    onPrimary = onPrimary,
    onSecondary = onSecondary,
    surface = primary,
    onSurface = surface,
    onBackground = background,
    background = primaryDark
)

private val LightColorPalette = lightColors(
    primary = primary,
    primaryVariant = primaryVariant,
    secondary = secondary,
    secondaryVariant = secondaryVariant,
    surface = onPrimary,
    onPrimary = onPrimary,
    onSecondary = onSecondary,
    onSurface = onSurface,
    onBackground = onBackground,
    background = onPrimary
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
