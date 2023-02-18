package ink.bluecloud.ui.fragment.mediaplayer.javafxmediaplayer

import ink.bluecloud.ui.fragment.mediaplayer.javafxmediaplayer.node.ControlBar
import ink.bluecloud.utils.ioScope
import ink.bluecloud.utils.uiutil.sceneRoot
import javafx.scene.input.MouseEvent
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import javafx.scene.paint.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import tornadofx.fitToParentSize
import tornadofx.style

data class PlayingData(
    val videoUrl: String,
    val audioUrl: String
)

@Factory
class VideoPlayer(data: PlayingData) : VideoPlayerNodes() {
    lateinit var videoPlayer: MediaPlayer
    var videoView: MediaView
    lateinit var audioPlayer: MediaPlayer

    init {
        VideoPlayerController(this, data)

        children += MediaView(videoPlayer).apply {
            boundsInParentProperty().addListener { _, _, _ ->
                fitWidthProperty().bind(sceneRoot.heightProperty())
                fitHeightProperty().bind(sceneRoot.widthProperty())
            }
            isPreserveRatio = true

        }.apply {
            videoView = this
        }

        style {
            backgroundColor += Color.BLACK
        }

        val registerForControllerBar = registerForControllerBar()
        parentProperty().addListener { _, _, newValue ->
            if (newValue == null) {
                timer?.cancel()
                registerForControllerBar.cancel()
            }
        }

        boundsInParentProperty().addListener { _, _, _ ->
            fitToParentSize()
        }
    }

    fun back() {
        videoPlayer.dispose()
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