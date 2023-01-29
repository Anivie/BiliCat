@file:Suppress("DuplicatedCode", "unused")

package ink.bluecloud.utils.uiutil

import ink.bluecloud.utils.ioContext
import ink.bluecloud.utils.newIO
import ink.bluecloud.utils.onUI
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

abstract class SuspendObservableEventHandler<T> : ChangeListener<T> {
    lateinit var continuation: Continuation<ObservableChangeEvent<T>>
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

private fun <T> getSuspendObservableEventHandler() = object : SuspendObservableEventHandler<T>() {
    override fun changed(observable: ObservableValue<out T>?, oldValue: T, newValue: T) {
        continuation.resume(ObservableChangeEvent(observable,oldValue,newValue))
    }
}

private fun <T> ObservableValue<T>.regSuspendObservableHandler() = getSuspendObservableEventHandler<T>().apply {
    addListener(this)
}

context(KoinComponent)
fun <T> ObservableValue<T>.newSuspendEventHandler(block: suspend ObservableChangeEvent<T>.() -> Unit) = newIO {
    val handler = regSuspendObservableHandler()

    while (isActive) {
        val event = suspendCoroutine {
            handler.continuation = it
        }

        onUI { block(event) }

        if (event.cancel) {
            removeListener(handler)
            cancel()
        }
    }
}
context(KoinComponent, CoroutineScope)
suspend fun <T> ObservableValue<T>.suspendEventHandler(block: suspend ObservableChangeEvent<T>.() -> Unit) = launch (ioContext) {
    val handler = regSuspendObservableHandler()

    while (isActive) {
        val event = suspendCoroutine {
            handler.continuation = it
        }

        onUI { block(event) }

        if (event.cancel) {
            removeListener(handler)
            cancel()
        }
    }
}
