package ink.bluecloud.ui.fragment.javafxmediaplayer

import ink.bluecloud.ui.fragment.javafxmediaplayer.node.ControlBar
import ink.bluecloud.utils.ioScope
import javafx.beans.binding.Bindings
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import tornadofx.stackpane
import kotlin.collections.minusAssign
import kotlin.collections.plusAssign
import kotlin.collections.set

data class PlayingData(
    val videoUrl: String,
    val audioUrl: String
)

@Factory
class VideoPlayer(data: PlayingData):VideoPlayerNodes() {

    val videoPlayer:MediaPlayer
    val audioPlayer:MediaPlayer

    init {
        videoPlayer = builder.buildPlayer(data.videoUrl)

        audioPlayer = builder.buildPlayer(data.audioUrl).apply {
            currentTimeProperty().isNotEqualTo(videoPlayer.currentTimeProperty()).addListener { _, _, newValue ->
                if (!newValue) return@addListener
                seek(videoPlayer.currentTime)
            }
        }

        Bindings.createBooleanBinding({
            (videoPlayer.status == MediaPlayer.Status.READY) && (audioPlayer.status == MediaPlayer.Status.READY)
        }, videoPlayer.statusProperty(), audioPlayer.statusProperty()).apply {
            this@VideoPlayer.properties["readyListener"] = this//for keep reference

            addListener { _, _, newValue ->
                if (!newValue) return@addListener

                videoPlayer.play()
                audioPlayer.play()
            }
        }

        stackpane {
            children += MediaView(videoPlayer).apply {
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

        val registerForControllerBar = registerForControllerBar()
        parentProperty().addListener { _, _, newValue ->
            if (newValue == null) {
                timer?.cancel()
                registerForControllerBar.cancel()
            }
        }

        Bindings.createObjectBinding({
            audioPlayer.error ?: audioPlayer.error
        },audioPlayer.errorProperty(),audioPlayer.errorProperty()).apply {
            this@VideoPlayer.properties["errorListener"] = this//for keep reference

            addListener { _, _, newValue ->
                newValue.printStackTrace()
                back(audioPlayer, audioPlayer)
            }
        }

        setOnMouseClicked {
            back(videoPlayer, audioPlayer)
        }

        VideoPlayerController(this)
    }

    private fun back(player: MediaPlayer, audioPlayer: MediaPlayer) {
        (this@VideoPlayer.parent as Pane).children -= this@VideoPlayer
        player.dispose()
        audioPlayer.dispose()
    }

    private fun registerForControllerBar() = ioScope.launch timerScope@{
        addEventFilter(MouseEvent.MOUSE_MOVED) {
            if (timerTarget.value != it.target::class) timerTarget.value = it.target::class
        }

        timerTarget.addListener { _, _, newValue ->
            when (newValue) {
                VideoPlayer::class -> {
                    if (!controlBar.isVisible) return@addListener
                    timer = ioScope.launch { timer() }
                }

                MediaView::class -> {
                    controlBar.isVisible = true
                    timer?.cancel()
                }

                ControlBar::class -> {
                    timer?.cancel()
                    timer = ioScope.launch { timer() }
                }
                else -> {}
            }
        }
    }

    private suspend fun timer() {
        delay(timerValue)
        controlBar.isVisible = false
    }
}