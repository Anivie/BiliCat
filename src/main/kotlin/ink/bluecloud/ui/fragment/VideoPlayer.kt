package ink.bluecloud.ui.fragment

import ink.bluecloud.service.clientservice.video.player.VideoPlayerBuilder
import ink.bluecloud.utils.uiScope
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import tornadofx.*


class VideoPlayer: KoinComponent,Fragment() {

    override val root = stackpane {
        uiScope.launch {
            children += MediaView(getPlayer().apply {
                isAutoPlay = true

                prefWidthProperty().bind(this@stackpane.widthProperty())
                prefHeightProperty().bind(this@stackpane.heightProperty())
            })

            prefWidthProperty().bind(currentStage!!.widthProperty())
            prefHeightProperty().bind(currentStage!!.heightProperty())
        }
    }

    private fun getPlayer():MediaPlayer {
        return get<VideoPlayerBuilder>().buildPlayer(params["url"]as String)
    }
}