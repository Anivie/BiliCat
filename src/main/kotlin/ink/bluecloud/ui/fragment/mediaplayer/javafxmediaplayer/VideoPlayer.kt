package ink.bluecloud.ui.fragment.mediaplayer.javafxmediaplayer

import ink.bluecloud.ui.fragment.mediaplayer.javafxmediaplayer.node.ControlBar
import ink.bluecloud.utils.ioScope
import javafx.beans.binding.Bindings
import javafx.scene.input.MouseEvent
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import javafx.scene.paint.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import tornadofx.fitToParentSize
import tornadofx.stackpane
import tornadofx.style
import kotlin.collections.set

data class PlayingData(
    val videoUrl: String,
    val audioUrl: String
)

@Factory
class VideoPlayer(data: PlayingData): VideoPlayerNodes() {

    val videoPlayer:MediaPlayer
    lateinit var videoView:MediaView
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

        stackpane player@{
            children += MediaView(videoPlayer).apply {
                isPreserveRatio = true

                fitWidthProperty().bind(this@player.widthProperty())
                fitHeightProperty().bind(this@player.heightProperty())

                videoView = this
            }

            style {
                backgroundColor += Color.BLACK
            }

            children += ControlBar().apply {
                controlBar = this
            }
            fitToParentSize()
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
                back()
            }
        }

        VideoPlayerController(this)
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