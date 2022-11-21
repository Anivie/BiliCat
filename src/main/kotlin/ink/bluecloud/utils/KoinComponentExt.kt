@file:Suppress("unused")

package ink.bluecloud.utils

import kotlinx.coroutines.CoroutineScope
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