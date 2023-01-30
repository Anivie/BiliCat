package ink.bluecloud.ui.fragment.videoview

import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.service.comments.info.load.CommentAreaLazyLoad
import ink.bluecloud.ui.fragment.mediaplayer.javafxmediaplayer.PlayingData
import ink.bluecloud.ui.fragment.mediaplayer.javafxmediaplayer.VideoPlayer
import ink.bluecloud.utils.newIO
import ink.bluecloud.utils.onUI
import ink.bluecloud.utils.sceneRoot
import javafx.scene.paint.Color
import org.koin.core.annotation.Factory
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import tornadofx.style

@Factory
class VideoView(
    card: HomePagePushCard
): VideoViewNodes() {
    init {
        newIO {
            val (video, audio) = videoStream.getVideoStream(
                card.id,
                card.cid
            )

            onUI {
                val player = get<VideoPlayer> {
                    parametersOf(
                        PlayingData(
                            video.get().url.first(),
                            audio.get().url.first()
                        )
                    )
                }

                player.controlBar.backButton.setOnAction {
                    sceneRoot.children -= this@VideoView
                    player.back()
                }

                heightProperty().map {
                    it.toDouble() / 2.0
                }.run {
                    player.prefHeightProperty().bind(this)
                }

                top =  player
            }

            get<CommentAreaLazyLoad>().getCommentAreaInfo(card.id).run {
                data?.replies?.forEach {

                }
            }
        }

        style {
            backgroundColor += Color.WHITE
        }
    }
}