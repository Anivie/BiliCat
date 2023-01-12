@file:Suppress("NOTHING_TO_INLINE")

package ink.bluecloud.utils.uiutil

import ink.bluecloud.cloudtools.CLOUD_INTERPOLATOR
import javafx.animation.Timeline
import javafx.beans.value.WritableValue
import javafx.util.Duration
import tornadofx.*

inline fun cloudTimeline(block: (Timeline).() -> Unit) = Timeline(165.0).apply {
    block()
}

inline fun <T> animationTo(vararg writableValue: WritableValue<T>, endValue: T, duration: Duration = Duration.millis(150.0), frame: Double = 165.0) = Timeline(frame).apply {
    keyframe(duration) {
        writableValue.forEach {
            keyvalue(it, endValue, CLOUD_INTERPOLATOR)
        }
    }
    play()
}