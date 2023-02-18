package ink.bluecloud.ui.fragment.videoview

import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.ui.fragment.mediaplayer.javafxmediaplayer.PlayingData
import ink.bluecloud.ui.fragment.mediaplayer.javafxmediaplayer.VideoPlayer
import ink.bluecloud.utils.newIO
import ink.bluecloud.utils.onUI
import ink.bluecloud.utils.uiutil.sceneRoot
import javafx.geometry.Pos
import javafx.scene.paint.Color
import org.koin.core.annotation.Factory
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import tornadofx.*

@Factory
class VideoView(
    card: HomePagePushCard
) : VideoViewNodes() {
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
                hbox {
                    button(/*"\uE629"*/"<") {
                        stylesheets += "css/node/suspended-button.css"

/*
                        skinProperty().addListener { _, _, _ ->
                            style(true) {
                                fontFamily = "mediaplayer"
                            }
                        }
*/

                        action {
                            sceneRoot.children -= this@VideoView
                            player.back()
                        }
                    }

                    alignment = Pos.CENTER_LEFT
                }

                scrollpane {
                    content = vbox {
                        children += player
                        textarea {  }

                        skinProperty().addListener { _, _, _ -> fitToParentSize() }
                    }

                    boundsInParentProperty().addListener { _, _, _ -> fitToParentSize() }
                }
            }
        }

        style {
            backgroundColor += Color.WHITE
            backgroundRadius += box(10.px)
        }

        boundsInParentProperty().addListener { _, _, _ -> fitToParentSize() }
        paddingAll = 10
    }
}