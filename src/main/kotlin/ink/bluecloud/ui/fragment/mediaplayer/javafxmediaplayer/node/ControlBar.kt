package ink.bluecloud.ui.fragment.mediaplayer.javafxmediaplayer.node

import ink.bluecloud.utils.uiutil.CssNode
import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*

class ControlBar: ControlBarNodes() {
    init {
        top = borderpane {
            left {
                backButton = button("\uE629")
            }

            style {
                backgroundColor += Color.WHITE
            }

            paddingAll = 5
            minHeight = 50.0
        }

        bottom = vbox(5) {
            borderpane {
                left = hbox {
                    listButton = button("\uE7F4")
                }

                center = hbox(15,Pos.CENTER) {
                    lastButton = button("\uEA44")

                    playButton = button("\uEA81"){
                        style {
                            fontSize = 40.px
                        }
                    }

                    nextButton = button("\uEA47")
                }

                right = hbox(10) {
                    label("\uE67C") {
                        style {
                            fontSize = 20.px
                        }
                    }

                    volumeBar = progressbar(0.3)

                    label("\uE67B") {
                        style {
                            fontSize = 20.px
                        }
                    }
                }
            }

            progressBar = progressbar(0.5) {
                fitToParentWidth()
            }

            style {
                backgroundColor += Color.WHITE
            }

            paddingAll = 5
            minHeight = 50.0
        }

        style {
            fontFamily = "mediaplayer"
        }

        stylesheets += CssNode.floatingButton
        stylesheets += "ui/mediaplayer/icon_player.css"
    }
}