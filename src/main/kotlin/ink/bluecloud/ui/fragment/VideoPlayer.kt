package ink.bluecloud.ui.fragment

import ink.bluecloud.service.clientservice.video.player.VideoPlayerBuilder
import javafx.scene.layout.StackPane
import javafx.scene.media.MediaView
import org.koin.core.annotation.Factory
import org.koin.core.component.KoinComponent
import org.koin.core.component.get


@Factory
class VideoPlayer(
    url: String
): KoinComponent,StackPane() {

    init {
        val player = get<VideoPlayerBuilder>().buildPlayer(url)

        children += MediaView(player.apply {
            isAutoPlay = true

            errorProperty().addListener { _, _, newValue ->
                newValue.printStackTrace()
            }
        })
    }

}