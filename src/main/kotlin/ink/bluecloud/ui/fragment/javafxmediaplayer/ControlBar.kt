package ink.bluecloud.ui.fragment.javafxmediaplayer

import ink.bluecloud.ui.CssNode
import javafx.scene.layout.BorderPane
import org.koin.core.component.KoinComponent
import tornadofx.*

class ControlBar: KoinComponent, BorderPane() {
    init {
        top = borderpane {
            left = button("<") {
                stylesheets += CssNode.floatingButton
            }

            minHeight = 50.0
        }

        bottom = borderpane {

        }
    }
}