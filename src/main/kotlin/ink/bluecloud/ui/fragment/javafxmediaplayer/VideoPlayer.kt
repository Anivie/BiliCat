package ink.bluecloud.ui.fragment.javafxmediaplayer

import ink.bluecloud.utils.uiScope
import javafx.beans.binding.Bindings
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import kotlinx.coroutines.*
import org.koin.core.annotation.Factory
import tornadofx.*

data class PlayingData(
    val videoUrl: String,
    val audioUrl: String
)

@Factory
class VideoPlayer(data: PlayingData):VideoPlayerNodes() {

    init {
        println(data.videoUrl)
        println(data.audioUrl)

        val player = builder.buildPlayer(data.videoUrl).apply {
            isAutoPlay = true
        }

        val audioPlayer = builder.buildPlayer(data.videoUrl).apply {
            isAutoPlay = true
            currentTimeProperty().addListener { _, _, newValue -> println(newValue) }

            currentTimeProperty().isNotEqualTo(player.currentTimeProperty()).addListener { _, _, newValue ->
                if (!newValue) return@addListener
                seek(player.currentTime)
            }
        }

        stackpane {
            children += MediaView(player).apply {
                fitWidthProperty().bind(this@VideoPlayer.widthProperty())
                fitHeightProperty().bind(this@VideoPlayer.heightProperty())

                layoutBoundsProperty().addListener { _, _, newValue ->
                    this@stackpane.maxWidth = newValue.width
                    this@stackpane.maxHeight = newValue.height
                }
            }

            children += ControlBar().apply {
                controlBar = this
            }
        }

        registerForControllerBar()

        player.errorProperty().addListener { _, _, newValue ->
            newValue.printStackTrace()
            back(player, audioPlayer)
        }

        Bindings.createObjectBinding({
            player.error ?: audioPlayer.error
        },player.errorProperty(),audioPlayer.errorProperty()).addListener { _, _, newValue ->
            back(player, audioPlayer)
        }

        setOnMouseClicked {
            back(player, audioPlayer)
        }
    }

    private fun back(player: MediaPlayer, audioPlayer: MediaPlayer) {
        (this@VideoPlayer.parent as Pane).children -= this@VideoPlayer
        player.dispose()
        audioPlayer.dispose()
    }

    private fun registerForControllerBar() {
        controlBar.run {
            addEventFilter(MouseEvent.MOUSE_EXITED) {
                if (job?.isActive == true) job?.cancel()
                job = uiScope.launch { timer() }
            }

            addEventFilter(MouseEvent.MOUSE_ENTERED) {
                timer.value = 5.0
                job?.cancel()
            }
        }

        addEventFilter(MouseEvent.MOUSE_MOVED) {
            if (it.target !is MediaView) return@addEventFilter

            timer.value = 5.0
            if (job?.isActive == true) job?.cancel()
            if (!controlBar.isVisible) controlBar.isVisible = true
        }

        timer.addListener { _, _, newValue ->
            if (newValue.toDouble() == 0.0) controlBar.isVisible = false
        }

        parentProperty().addListener { _, _, newValue ->
            if (newValue == null) job?.cancel()
        }
    }

    private suspend fun timer() {
        coroutineScope {
            while (isActive) {
                if (controlBar.isHover) return@coroutineScope

                if (timer.value > 0.0) {
                    timer.value -= 1.0
                } else cancel()

                delay(1000)
            }
        }
    }
}