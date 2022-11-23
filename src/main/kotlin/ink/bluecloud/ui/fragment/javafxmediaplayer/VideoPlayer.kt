package ink.bluecloud.ui.fragment.javafxmediaplayer

import ink.bluecloud.service.clientservice.video.player.VideoPlayerBuilder
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.media.MediaView
import org.koin.core.annotation.Factory
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import tornadofx.*


@Factory
class VideoPlayer(
    url: String
): KoinComponent,StackPane() {

    init {
        val player = get<VideoPlayerBuilder>().buildPlayer(url).apply {
            isAutoPlay = true
        }.apply {
            errorProperty().addListener { _, _, newValue ->
                newValue.printStackTrace()
                (parent as Pane).children -= this@VideoPlayer
                stop()
            }
        }

        stackpane {
            children += MediaView(player).apply {
                fitWidthProperty().bind(this@VideoPlayer.widthProperty())
                fitHeightProperty().bind(this@VideoPlayer.heightProperty())
            }

            setOnMouseClicked {
                (parent as Pane).children -= this@VideoPlayer
                player.stop()
            }
        }
    }
}