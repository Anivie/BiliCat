@file:Suppress("DuplicatedCode", "unused", "UNCHECKED_CAST")

package ink.bluecloud.utils.uiutil

import ink.bluecloud.utils.ioContext
import ink.bluecloud.utils.newIO
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.resume

abstract class SuspendObservableEventHandler<T> : ChangeListener<T> {
    companion object {
        val eventMap = ConcurrentHashMap<Any,Any>()
    }
    lateinit var continuation: CancellableContinuation<ObservableChangeEvent<T>>
}

data class ObservableChangeEvent <T>(
    val observableValue: ObservableValue<out T>?,
    val oldValue: T,
    val newValue: T,
    var cancel: Boolean = false
) {
    @Suppress("NOTHING_TO_INLINE")
    inline fun cancel() {
        cancel = true
    }
}

private fun <T> ObservableValue<T>.getSuspendObservableEventHandler() = object : SuspendObservableEventHandler<T>() {
    override fun changed(observable: ObservableValue<out T>?, oldValue: T, newValue: T) {
        if (continuation.isActive) {
            continuation.resume(ObservableChangeEvent(observable, oldValue, newValue))
            return
        }

        (eventMap[this@getSuspendObservableEventHandler]as? ArrayDeque<ObservableChangeEvent<T>>)?.run {
            this += ObservableChangeEvent(observable, oldValue, newValue)
        }?: run {
            eventMap[this@getSuspendObservableEventHandler] = ArrayDeque<ObservableChangeEvent<T>>().apply {
                this += ObservableChangeEvent(observable, oldValue, newValue)
            }
        }
    }
}

private fun <T> ObservableValue<T>.regSuspendObservableHandler() = getSuspendObservableEventHandler<T>().apply {
    addListener(this)
}

context(KoinComponent)
fun <T> ObservableValue<T>.newSuspendEventHandler(block: suspend ObservableChangeEvent<T>.() -> Unit) = newIO {
    val handler = regSuspendObservableHandler()

    while (isActive) {
        val event = suspendCancellableCoroutine {
            handler.continuation = it
        }

        processEvent(block, event)

        if (event.cancel) {
            removeListener(handler)
            cancel()
        }
    }
}

private suspend fun <T> ObservableValue<T>.processEvent(
    block: suspend ObservableChangeEvent<T>.() -> Unit,
    event: ObservableChangeEvent<T>
) {
    block(event)

    (SuspendObservableEventHandler.eventMap[this@processEvent] as? ArrayDeque<ObservableChangeEvent<T>>)?.run {
        if (isNotEmpty()) {
            block(removeFirst())
            if (isNotEmpty()) processEvent(block, removeFirst())
        }
        if (isEmpty()) SuspendObservableEventHandler.eventMap.remove(this@processEvent)
    }
}

context(KoinComponent, CoroutineScope)
suspend fun <T> ObservableValue<T>.suspendEventHandler(block: suspend ObservableChangeEvent<T>.() -> Unit) = launch (ioContext) {
    val handler = regSuspendObservableHandler()
    while (isActive) {
        val event = suspendCancellableCoroutine {
            handler.continuation = it
        }

        block(event)

        if (event.cancel) {
            removeListener(handler)
            cancel()
        }
    }
}