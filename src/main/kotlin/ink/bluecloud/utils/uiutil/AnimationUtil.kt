@file:Suppress("NOTHING_TO_INLINE")

package ink.bluecloud.utils.uiutil

import ink.bluecloud.cloudtools.CLOUD_INTERPOLATOR
import javafx.animation.Animation
import javafx.animation.Timeline
import javafx.beans.value.WritableValue
import javafx.scene.Node
import javafx.util.Duration
import tornadofx.*

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
inline fun cloudTimeline(node: Node? = null, block: (Timeline).() -> Unit) = Timeline(165.0).apply {
    node?.run {
        statusProperty().addListener { _, _, newValue ->
            isDisable = when (newValue) {
                Animation.Status.PAUSED -> false
                Animation.Status.RUNNING -> true
                Animation.Status.STOPPED -> false
            }
        }
    }

    block()
}

inline fun <T> animationTo(vararg writableValue: WritableValue<T>, endValue: T, duration: Duration = Duration.millis(150.0), frame: Double = 165.0) = Timeline(frame).apply {
    keyframe(duration) {
        writableValue.forEach {
            keyvalue(it, endValue, CLOUD_INTERPOLATOR)
        }
    }
}