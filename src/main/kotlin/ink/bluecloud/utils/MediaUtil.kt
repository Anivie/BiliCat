@file:Suppress("NOTHING_TO_INLINE")

package ink.bluecloud.utils

import com.sun.media.jfxmedia.locator.Locator
import javafx.scene.media.Media
import java.util.*

@Suppress("UNCHECKED_CAST")
inline fun Media.init(cookie: String) {
    val locator = javaClass.getDeclaredField("jfxLocator").apply {
        isAccessible = true
    }.get(this)as Locator

    val map = locator.javaClass.getDeclaredField("connectionProperties").apply {
        isAccessible = true
    }.get(locator)as TreeMap<String, Any>
    map["cookie"] = cookie
}