@file:Suppress("NOTHING_TO_INLINE")

package ink.bluecloud.ui

import ink.bluecloud.cloudtools.CLOUD_INTERPOLATOR
import javafx.animation.Timeline
import javafx.beans.value.WritableValue
import javafx.util.Duration
import tornadofx.*

inline fun cloudTimeline(block: (Timeline).() -> Unit) = Timeline(165.0).apply {
    block()
}

inline fun <T> animationTo(vararg writableValue: WritableValue<T>,endValue: T, duration: Duration = Duration.millis(150.0),frame: Double = 165.0) = Timeline(frame).apply {
    keyframe(duration) {
        writableValue.forEach {
            keyvalue(it, endValue, CLOUD_INTERPOLATOR)
        }
    }
    play()
}

//inline fun UIComponent.cloudnotice(type: NoticeType, message: String) = CloudNotice(type, message, primaryStage).show()
//inline fun Stage.windowHandle() = (MethodHandles.privateLookupIn(Window::class.java, MethodHandles.lookup()).findVarHandle(Window::class.java,"peer", TKStage::class.java)[this]as TKStage).rawHandle

@Suppress("unused")
object HarmonySans {
    const val BOLD = "HarmonyOS Sans SC Bold"
    const val Medium = "HarmonyOS Sans SC Medium"
    const val REGULAR = "HarmonyOS Sans SC"
}

object CssNode {
    const val floatingButton = "css/node/suspended-button.css"
}