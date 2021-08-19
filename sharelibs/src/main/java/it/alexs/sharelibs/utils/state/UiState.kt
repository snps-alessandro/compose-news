package it.alexs.sharelibs.utils.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart

data class UiState<T>(
    val loading: Boolean = false,
    val exception: Throwable? = null,
    val data: T? = null
) {

    val isFailure: Boolean
        get() = exception != null

    val initialLoading: Boolean
        get() = data == null && loading && !isFailure
}

fun <T> UiState<T>.copyWithResult(value: T): UiState<T> {
    return copy(loading = false, exception = null, data = value)
}

fun <T> UiState<T>.copyWithException(exception: Throwable?): UiState<T> {
    return copy(loading = false, exception = exception)
}

@Composable
fun <Producer, T> produceUiState(
    producer: Producer,
    block: suspend Producer.() -> Flow<T>
) = produceUiState(producer = producer, key = Unit, block = block)

@Composable
fun <Producer, T> produceUiState(
    producer: Producer,
    key: Any?,
    block: suspend Producer.() -> Flow<T>
): ProducerResult<UiState<T>> {

    val refresh = remember {
        block
    }

    val scope = rememberCoroutineScope()

    val result = produceState(initialValue = UiState<T>(loading = true), producer, key) {
        value = UiState(loading = true)

        producer.block()
            .catch { e -> value = value.copyWithException(e) }
            .onStart { value = value.copy(loading = true) }
            .collectLatest { value = value.copyWithResult(it) }
    }

    return ProducerResult(
        result = result,
        onRefresh = {}
    )
}

data class ProducerResult<T>(
    val result: State<T>,
    val onRefresh: () -> Unit
)
