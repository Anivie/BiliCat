package ink.bluecloud.ui.fragment.javafxmediaplayer.node

import javafx.scene.control.Button
import javafx.scene.control.ProgressBar
import javafx.scene.layout.BorderPane
import org.koin.core.component.KoinComponent
import tornadofx.*

abstract class ControlBarNodes: KoinComponent, BorderPane() {
    val timerValue = 3.0

    var backButton by singleAssign<Button>()

    var listButton by singleAssign<Button>()
    var lastButton by singleAssign<Button>()
    var playButton by singleAssign<Button>()
    var nextButton by singleAssign<Button>()

    var volumeBar by singleAssign<ProgressBar>()
    var progressBar by singleAssign<ProgressBar>()
}