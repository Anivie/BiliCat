package ink.bluecloud.ui.fragment.javafxmediaplayer

import ink.bluecloud.ui.CssNode
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import org.koin.core.component.KoinComponent
import tornadofx.*

class ControlBar: KoinComponent, BorderPane() {
    init {
        top = borderpane {
            left = button("\uE629")

            style {
                backgroundColor += Color.WHITE
            }

            paddingAll = 5
            minHeight = 50.0
        }

        bottom = vbox(5) {
            borderpane {
                left = hbox {
                    button("\uE7F4")
                }

                center = hbox(15,Pos.CENTER) {
                    button("\uEA44")
                    button("\uEA82").style {
                        fontSize = 40.px
                    }
                    button("\uEA47")
                }

                right = hbox(10) {
                    label("\uE67C") {
                        style {
                            fontSize = 20.px
                        }
                    }
                    progressbar(0.3)
                    label("\uE67B") {
                        style {
                            fontSize = 20.px
                        }
                    }
                }
            }

            progressbar(0.5) {
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