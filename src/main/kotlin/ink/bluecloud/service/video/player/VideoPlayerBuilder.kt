package ink.bluecloud.service.video.player

import com.sun.media.jfxmedia.locator.Locator
import ink.bluecloud.service.ClientService
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import org.koin.core.annotation.Factory
import java.util.*

@Factory
class VideoPlayerBuilder: ClientService() {
    @Suppress("UNCHECKED_CAST")
    fun buildPlayer(url:String): MediaPlayer {
        return MediaPlayer(Media(url).apply {
            val locator = javaClass.getDeclaredField("jfxLocator").apply {
                isAccessible = true
            }.get(this)as Locator

            val map = locator.javaClass.getDeclaredField("connectionProperties").apply {
                isAccessible = true
            }.get(locator)as TreeMap<String, Any>

            map["cookie"] = httpClient.getCookieStore().toString()
        })
    }
}