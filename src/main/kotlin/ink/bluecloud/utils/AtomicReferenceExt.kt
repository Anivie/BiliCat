@file:Suppress("NOTHING_TO_INLINE")

package ink.bluecloud.utils

import java.util.concurrent.atomic.AtomicReference
import kotlin.reflect.KProperty

inline operator fun <Y> AtomicReference<Y>.getValue(x: Any?, p: KProperty<*>) : Y = this.get()

inline operator fun <Y> AtomicReference<Y>.setValue(x: Any?, p: KProperty<*>, value: Y) { this.set(value) }