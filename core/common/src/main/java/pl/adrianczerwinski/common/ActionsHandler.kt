package pl.adrianczerwinski.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Composable
fun <UiAction> HandleAction(
    action: SharedFlow<UiAction>,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    handler: suspend (UiAction) -> Unit
) = DisposableEffect(Unit) {
    val job = coroutineScope.launch {
        action.collect { handler.invoke(it) }
    }
    onDispose { job.cancel() }
}
