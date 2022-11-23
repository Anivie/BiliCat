package ink.bluecloud.ui.fragment.javafxmediaplayer

import javafx.scene.layout.BorderPane
import kotlinx.coroutines.Dispatchers
import tornadofx.*

class ControlBar:BorderPane() {
    init {
        top = borderpane {
            left = label("<")
        }

        bottom = borderpane {

        }
    }
}