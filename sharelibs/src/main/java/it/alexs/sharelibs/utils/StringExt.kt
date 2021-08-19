package it.alexs.sharelibs.utils

fun String?.orPlaceholder(prefix: String? = null): String {
    return if (prefix.isNullOrEmpty()) {
        this ?: "-"
    } else {
        "$prefix: ${this ?: "-"}"
    }
}