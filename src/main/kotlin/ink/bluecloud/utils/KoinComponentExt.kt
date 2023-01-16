@file:Suppress("unused")

package ink.bluecloud.utils

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named

internal inline val KoinComponent.koin
    get() = getKoin()

internal inline val KoinComponent.ioScope
    get() = get<CoroutineScope>(named("ioScope"))

internal inline val KoinComponent.ioContext
    get() = get<CoroutineScope>(named("ioScope")).coroutineContext

internal inline val KoinComponent.uiScope
    get() = get<CoroutineScope>(named("uiScope"))

internal inline val KoinComponent.uiContext
    get() = get<CoroutineScope>(named("uiScope")).coroutineContext

internal inline fun <T> KoinComponent.newIO(name: String? = null, crossinline block: suspend CoroutineScope.() -> T) {
    name?.run {
        ioScope.launch(CoroutineName(this)) {
            block()
        }
    }?: ioScope.launch {
        block()
    }
}

internal inline fun <T> KoinComponent.newUI(name: String? = null, crossinline block: suspend CoroutineScope.() -> T) {
    name?.run {
        uiScope.launch(CoroutineName(this)) {
            block()
        }
    }?: uiScope.launch {
        block()
    }
}

internal suspend inline fun <T> KoinComponent.onIO(name: String? = null, crossinline block: suspend CoroutineScope.() -> T):T {
    return name?.run {
        withContext(ioContext + CoroutineName(this)) {
            block()
        }
    } ?: withContext(ioContext) {
        block()
    }
}

internal suspend inline fun <T> KoinComponent.onUI(name: String? = null, crossinline block: suspend CoroutineScope.() -> T):T {
    return name?.run {
        withContext(uiContext + CoroutineName(this)) {
            block()
        }
    } ?: withContext(uiContext) {
        block()
    }
}