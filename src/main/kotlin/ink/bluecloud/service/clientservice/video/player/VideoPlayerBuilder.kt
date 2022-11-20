package ink.bluecloud.service.clientservice.video.player

import ink.bluecloud.service.ClientService
import ink.bluecloud.utils.init
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import org.koin.core.annotation.Factory

@Factory
class VideoPlayerBuilder: ClientService() {
    fun buildPlayer(url:String): MediaPlayer {
        return MediaPlayer(Media(url).apply {
            init(httpClient.getCookieStore().toCookies().toString().replace("\n",""))
        })
    }
}