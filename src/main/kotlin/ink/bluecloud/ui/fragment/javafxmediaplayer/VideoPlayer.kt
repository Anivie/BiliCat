package ink.bluecloud.ui.fragment.javafxmediaplayer

import ink.bluecloud.service.clientservice.video.player.VideoPlayerBuilder
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.media.MediaView
import org.koin.core.annotation.Factory
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tornadofx.*


@Factory
class VideoPlayer(
    videoUrl: String,
    audioUrl: String
): KoinComponent,StackPane() {

    private val builder by inject<VideoPlayerBuilder>()

    init {
        println(videoUrl)
        println(audioUrl)
        val player = builder.buildPlayer(videoUrl).apply {
            isAutoPlay = true
        }.apply {
            errorProperty().addListener { _, _, newValue ->
                newValue.printStackTrace()
                (parent as Pane).children -= this@VideoPlayer
                dispose()
            }
        }

/*
        builder.buildPlayer(audioUrl).apply {
            isAutoPlay = true
            currentTimeProperty().isNotEqualTo(player.currentTimeProperty()).addListener { _, _, newValue ->
                if (!newValue) return@addListener
                seek(player.currentTime)
            }
        }
*/

        stackpane {
            children += MediaView(player).apply {
                fitWidthProperty().bind(this@VideoPlayer.widthProperty())
                fitHeightProperty().bind(this@VideoPlayer.heightProperty())

                layoutBoundsProperty().addListener { _, _, newValue ->
                    this@stackpane.maxWidth = newValue.width
                    this@stackpane.maxHeight = newValue.height
                }
            }

            children += ControlBar()
        }


        setOnMouseClicked {
            (this@VideoPlayer.parent as Pane).children -= this@VideoPlayer
            player.dispose()
        }

    }
}