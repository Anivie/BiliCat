@file:Suppress("NOTHING_TO_INLINE")

package ink.bluecloud.utils

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import kotlin.reflect.KProperty

inline operator fun <Y> AtomicReference<Y>.getValue(x: Any?, p: KProperty<*>) : Y = this.get()

inline operator fun <Y> AtomicReference<Y>.setValue(x: Any?, p: KProperty<*>, value: Y) { this.set(value) }
inline operator fun AtomicBoolean.getValue(x: Any?, p: KProperty<*>) : Boolean = this.get()

inline operator fun AtomicBoolean.setValue(x: Any?, p: KProperty<*>, value: Boolean) { this.set(value) }

