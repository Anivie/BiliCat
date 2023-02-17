package ink.bluecloud.ui.fragment.videoview

import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.ui.fragment.mediaplayer.javafxmediaplayer.PlayingData
import ink.bluecloud.ui.fragment.mediaplayer.javafxmediaplayer.VideoPlayer
import ink.bluecloud.utils.newIO
import ink.bluecloud.utils.onUI
import ink.bluecloud.utils.sceneRoot
import javafx.geometry.Pos
import javafx.scene.paint.Color
import org.koin.core.annotation.Factory
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import tornadofx.*

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

            val player = get<VideoPlayer> {
                parametersOf(
                    PlayingData(
                        video.get().url.first(),
                        audio.get().url.first()
                    )
                )
            }

            onUI {
                top = hbox {
                    button("\uE629") {
                        stylesheets += "css/node/suspended-button.css"
                        style(true) {
                            fontSize = 25.px
                            fontFamily = "mediaplayer"
                        }

                        action {
                            sceneRoot.children -= this@VideoView
                            player.back()
                        }
                    }

                    alignment = Pos.CENTER_LEFT
                }


                center = scrollpane {
                    borderpane {
                        heightProperty().map {
                            it.toDouble() / 2.0
                        }.run {
                            player.prefHeightProperty().bind(this)
                        }
                        center = player



                    }
                }


            }

/*
            get<CommentAreaLazyLoad>().getCommentAreaInfo(card.id).run {
                data?.replies?.forEach {

                }
            }
*/
        }

        style {
            backgroundColor += Color.WHITE
            backgroundRadius += box(10.px)
        }
        paddingAll = 10
    }
}