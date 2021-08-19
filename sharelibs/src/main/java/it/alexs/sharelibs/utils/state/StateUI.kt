@file:Suppress("UNCHECKED_CAST", "RedundantVisibilityModifier")

package it.alexs.sharelibs.utils.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart

class StateUI<out T> internal constructor(val value: Any?) {

    companion object {
        fun <T> success(value: T?): StateUI<T> = StateUI(value)

        fun <T> failure(exception: Throwable): StateUI<T> =
            StateUI(createFailure(exception))

        fun <T> loading(): StateUI<T> = StateUI(null)

        fun <T> completed(): StateUI<T> = StateUI(createComplete())
    }

    val isSuccess: Boolean get() = value !is Failure && value !is Complete && !isLoading

    val isFailure: Boolean get() = value is Failure

    val isLoading: Boolean get() = value == null && !isFailure

    val isComplete: Boolean get() = value is Complete

    internal class Failure(
        val exception: Throwable
    ) {
        override fun toString(): String {
            return "Failure: ${exception.localizedMessage}"
        }
    }

    internal class Complete(
        val completed: Boolean
    )

    public fun exceptionOrNull(): Throwable? =
        when (value) {
            is Failure -> value.exception
            else -> null
        }

    fun getOrNull(): T? = when (value) {
        is Failure, is Complete -> null
        else -> value as T?
    }
}

internal fun createFailure(exception: Throwable): Any = StateUI.Failure(exception)

internal fun createComplete(): Any = StateUI.Complete(true)


