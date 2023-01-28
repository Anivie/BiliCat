@file:Suppress("DuplicatedCode", "NOTHING_TO_INLINE")

package ink.bluecloud.utils.uiutil

import ink.bluecloud.utils.ioContext
import ink.bluecloud.utils.newIO
import ink.bluecloud.utils.onUI
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
    lateinit var continuation: Continuation<T>
}

data class EventExit(var cancel: Boolean = false) {
    @Suppress("NOTHING_TO_INLINE")
    inline fun cancel() {
        cancel = true
    }
}

inline fun <T: Event> getSuspendHandler() = object : SuspendEventHandler<T>() {
    override fun handle(event: T) {
        continuation.resume(event)
    }
}

inline fun <T: Event> Node.regSuspendHandler(eventType: EventType<T>) = getSuspendHandler<T>().apply {
    addEventHandler(eventType, this)
}

context(KoinComponent)
fun <T: Event> Node.newCoroutineEventHandler(eventType: EventType<T>, block: suspend EventExit.() -> Unit) = newIO {
    val handler = regSuspendHandler(eventType)
    val eventExit = EventExit()

    while (isActive) {
        suspendCoroutine {
            handler.continuation = it
        }

        onUI { block(eventExit) }

        if (eventExit.cancel) {
            removeEventHandler(eventType, handler)
            cancel()
        }
    }
}
context(KoinComponent, CoroutineScope)
suspend fun <T: Event> Node.coroutineEventHandler(eventType: EventType<T>, block: suspend EventExit.() -> Unit) = launch (ioContext) {
    val handler = getSuspendHandler<T>()
    addEventHandler(eventType, handler)

    val eventExit = EventExit()
    while (isActive) {
        suspendCoroutine {
            handler.continuation = it
        }

        onUI { block(eventExit) }

        if (eventExit.cancel) {
            removeEventHandler(eventType, handler)
            cancel()
        }
    }
}
