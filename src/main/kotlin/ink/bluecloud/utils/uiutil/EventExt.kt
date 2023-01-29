@file:Suppress("DuplicatedCode", "unused")

package ink.bluecloud.utils.uiutil

import ink.bluecloud.utils.ioContext
import ink.bluecloud.utils.newIO
import javafx.event.Event
import javafx.event.EventHandler
import javafx.event.EventType
import javafx.scene.Node
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

abstract class SuspendEventHandler<T: Event> : EventHandler<T> {
    lateinit var continuation: Continuation<CoroutineEvent<T>>
}

data class CoroutineEvent <T: Event>(
    val event: T,
    var cancel: Boolean = false
) {
    @Suppress("NOTHING_TO_INLINE")
    inline fun cancel() {
        event
        cancel = true
    }
}

private fun <T: Event> getSuspendHandler() = object : SuspendEventHandler<T>() {
    override fun handle(event: T) {
        continuation.resume(CoroutineEvent(event))
    }
}

private fun <T: Event> Node.regSuspendHandler(eventType: EventType<T>) = getSuspendHandler<T>().apply {
    addEventHandler(eventType, this)
}

context(KoinComponent)
fun <T: Event> Node.newSuspendEventHandler(eventType: EventType<T>, block: suspend CoroutineEvent<T>.() -> Unit) = newIO {
    val handler = regSuspendHandler(eventType)

    while (isActive) {
        val event = suspendCoroutine {
            handler.continuation = it
        }

        block(event)

        if (event.cancel) {
            removeEventHandler(eventType, handler)
            cancel()
        }
    }
}
context(KoinComponent, CoroutineScope)
suspend fun <T: Event> Node.suspendEventHandler(eventType: EventType<T>, block: suspend CoroutineEvent<T>.() -> Unit) = launch (ioContext) {
    val handler = regSuspendHandler(eventType)

    while (isActive) {
        val event = suspendCoroutine {
            handler.continuation = it
        }

        block(event)

        if (event.cancel) {
            removeEventHandler(eventType, handler)
            cancel()
        }
    }
}
