package ink.bluecloud.ui.fragment.javafxmediaplayer

import ink.bluecloud.ui.fragment.javafxmediaplayer.node.ControlBar
import ink.bluecloud.utils.uiScope
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

    val videoPlayer:MediaPlayer
    val audioPlayer:MediaPlayer

    init {
        videoPlayer = builder.buildPlayer(data.videoUrl)

        audioPlayer = builder.buildPlayer(data.audioUrl).apply {
            currentTimeProperty().addListener { _, _, newValue -> println(newValue) }

            currentTimeProperty().isNotEqualTo(videoPlayer.currentTimeProperty()).addListener { _, _, newValue ->
                if (!newValue) return@addListener
                seek(videoPlayer.currentTime)
            }
        }

        audioPlayer.statusProperty().addListener { _, _, newValue ->
            if (newValue == MediaPlayer.Status.READY && (videoPlayer.status == MediaPlayer.Status.READY)) {
                videoPlayer.play()
                audioPlayer.play()
            }
        }

        videoPlayer.statusProperty().addListener { _, _, newValue ->
            if (newValue == MediaPlayer.Status.READY && (audioPlayer.status == MediaPlayer.Status.READY)) {
                videoPlayer.play()
                audioPlayer.play()
            }
        }

/* todo: can't working but under code is working
        if ((videoPlayer.status == MediaPlayer.Status.READY) && (audioPlayer.status == MediaPlayer.Status.READY)) {
            videoPlayer.play()
            audioPlayer.play()
        }

        videoPlayer.statusProperty().isEqualTo(audioPlayer.statusProperty()).addListener { _, _, newValue ->
            println(newValue)
            if (!newValue) return@addListener
            videoPlayer.play()
            audioPlayer.play()
        }
        val status = Bindings.createBooleanBinding({
            (videoPlayer.status == MediaPlayer.Status.READY) && (audioPlayer.status == MediaPlayer.Status.READY)
        }, videoPlayer.statusProperty(), audioPlayer.statusProperty())

        status.addListener { _, _, newValue ->
            println(newValue)
            if (!newValue) return@addListener
            videoPlayer.play()
            audioPlayer.play()
        }
*/



        /*
                ioScope.launch {
                    while (true) {
                        println((videoPlayer.status == MediaPlayer.Status.READY) && (audioPlayer.status == MediaPlayer.Status.READY))
                        delay(500)
                    }
                }
        */


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
                playButton.userData = videoPlayer.statusProperty()

                controlBar = this
            }
        }

        registerForControllerBar()

/*
        Bindings.createObjectBinding({
            videoPlayer.error ?: audioPlayer.error
        },videoPlayer.errorProperty(),audioPlayer.errorProperty()).addListener { _, _, newValue ->
            newValue.printStackTrace()
            back(videoPlayer, audioPlayer)
        }
*/

        videoPlayer.errorProperty().addListener { _, _, newValue ->
            newValue.printStackTrace()
            back(videoPlayer, audioPlayer)
        }

        audioPlayer.errorProperty().addListener { _, _, newValue ->
            newValue.printStackTrace()
            back(videoPlayer, audioPlayer)
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