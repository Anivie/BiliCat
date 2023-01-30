package ink.bluecloud.ui.fragment.mediaplayer.javafxmediaplayer

import javafx.scene.media.MediaPlayer.Status
import org.koin.core.annotation.Factory
import org.koin.core.component.KoinComponent

@Factory
class VideoPlayerController(player: VideoPlayer):KoinComponent {

    init {
        player.controlBar.run {
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
    }

}