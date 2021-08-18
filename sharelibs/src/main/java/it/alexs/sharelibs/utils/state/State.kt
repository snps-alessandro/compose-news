@file:Suppress("UNCHECKED_CAST", "RedundantVisibilityModifier")

package it.alexs.sharelibs.utils.state

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class State<out T> internal constructor(val value: Any?) {

    companion object {
        fun <T> success(value: T?): State<T> = State(value)

        fun <T> failure(exception: Throwable): State<T> =
            State(createFailure(exception))

        fun <T> loading(): State<T> = State(null)

        fun <T> completed(): State<T> = State(createComplete())
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

internal fun createFailure(exception: Throwable): Any = State.Failure(exception)

internal fun createComplete(): Any = State.Complete(true)

fun <Producer, T> produceState(
    producer: Producer,
    coroutineScope: CoroutineScope,
    liveData: MutableLiveData<State<T>>,
    block: suspend Producer.() -> Flow<T?>
) {
    coroutineScope.launch {
        producer.block()
            .catch { e -> liveData.value = State.failure<T>(e) }
            .onStart { liveData.value = State.loading<T>() }
            .onCompletion { liveData.value = State.completed<T>() }
            .collectLatest { liveData.value = State.success(it) }
    }
}
