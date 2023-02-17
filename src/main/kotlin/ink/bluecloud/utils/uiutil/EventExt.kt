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
import kotlin.coroutines.resume

abstract class SuspendEventHandler<T : Event> : EventHandler<T> {
    lateinit var continuation: CancellableContinuation<CoroutineEvent<T>>
}

data class CoroutineEvent<T : Event>(
    val event: T,
    var cancel: Boolean = false
) {
    @Suppress("NOTHING_TO_INLINE")
    inline fun cancel() {
        cancel = true
    }
}

private const val listkey = "SuspendEventList"

@Suppress("UNCHECKED_CAST")
private fun <T : Event> Node.getSuspendHandler() = object : SuspendEventHandler<T>() {
    override fun handle(event: T) {
        if (continuation.isActive) {
            continuation.resume(CoroutineEvent(event))
            return
        }

        (properties[listkey] as? ArrayDeque<CoroutineEvent<T>>)?.run {
            this += CoroutineEvent(event)
        } ?: run {
            properties[listkey] = ArrayDeque<CoroutineEvent<T>>().apply {
                this += CoroutineEvent(event)
            }
        }
    }
}

private fun <T : Event> Node.regSuspendHandler(eventType: EventType<T>) = getSuspendHandler<T>().apply {
    addEventHandler(eventType, this)
}

context(KoinComponent)
fun <T : Event> Node.newSuspendEventHandler(eventType: EventType<T>, block: suspend CoroutineEvent<T>.() -> Unit) =
    newIO {
        val handler = regSuspendHandler(eventType)

        while (isActive) {
            val event = suspendCancellableCoroutine {
                handler.continuation = it
            }

            processEvent(block, event)

            if (event.cancel) {
                removeEventHandler(eventType, handler)
                cancel()
            }
        }
    }

context(KoinComponent, CoroutineScope)
suspend fun <T : Event> Node.suspendEventHandler(eventType: EventType<T>, block: suspend CoroutineEvent<T>.() -> Unit) =
    launch(ioContext) {
        val handler = regSuspendHandler(eventType)

        while (isActive) {
            val event = suspendCancellableCoroutine {
                handler.continuation = it
            }

            processEvent(block, event)

            if (event.cancel) {
                removeEventHandler(eventType, handler)
                cancel()
            }
        }
    }

@Suppress("UNCHECKED_CAST")
private suspend fun <T : Event> Node.processEvent(
    block: suspend CoroutineEvent<T>.() -> Unit,
    event: CoroutineEvent<T>
) {
    block(event)
    (properties[listkey] as? ArrayDeque<CoroutineEvent<T>>)?.run {
        if (isNotEmpty()) {
            block(removeFirst())
            if (isNotEmpty()) processEvent(block, removeFirst())
        }
        if (isEmpty()) properties.remove(listkey)
    }
}