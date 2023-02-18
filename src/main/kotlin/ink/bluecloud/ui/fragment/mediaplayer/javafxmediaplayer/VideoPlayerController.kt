package ink.bluecloud.ui.fragment.mediaplayer.javafxmediaplayer

import ink.bluecloud.ui.fragment.mediaplayer.javafxmediaplayer.node.ControlBar
import javafx.beans.binding.Bindings
import javafx.scene.media.MediaPlayer.Status
import org.koin.core.annotation.Factory
import org.koin.core.component.KoinComponent

@Factory
class VideoPlayerController(player: VideoPlayer, data: PlayingData) : KoinComponent {

    init {
        player.run {
            videoPlayer = builder.buildPlayer(data.videoUrl)

            audioPlayer = builder.buildPlayer(data.audioUrl).apply {
                currentTimeProperty().isNotEqualTo(videoPlayer.currentTimeProperty()).addListener { _, _, newValue ->
                    if (!newValue) return@addListener
                    seek(videoPlayer.currentTime)
                }
            }

            children += ControlBar().apply {
                controlBar = this
            }
            controlBar.run {
                player.videoPlayer.statusProperty().addListener { _, _, newValue ->
                    if (newValue == Status.PLAYING) {
                        playButton.text = "\uEA81"
                    } else playButton.text = "\uEA82"
                }

                playButton.setOnAction {
                    if (player.videoPlayer.statusProperty().get() == Status.PLAYING) {
                        player.videoPlayer.pause()
                        player.audioPlayer.pause()
                    } else {
                        player.videoPlayer.play()
                        player.audioPlayer.play()
                    }
                }
            }

            Bindings.createBooleanBinding({
                (videoPlayer.status == Status.READY) && (audioPlayer.status == Status.READY)
            }, videoPlayer.statusProperty(), audioPlayer.statusProperty()).apply {
                this@run.properties["readyListener"] = this//for keep reference

                addListener { _, _, newValue ->
                    if (!newValue) return@addListener

                    videoPlayer.play()
                    audioPlayer.play()
                }
            }

            Bindings.createObjectBinding({
                audioPlayer.error ?: audioPlayer.error
            }, audioPlayer.errorProperty(), audioPlayer.errorProperty()).apply {
                this@run.properties["errorListener"] = this//for keep reference

                addListener { _, _, newValue ->
                    newValue.printStackTrace()
                    back()
                }
            }
        }
    }
}