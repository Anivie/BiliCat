package ink.bluecloud.ui.mainview

import javafx.scene.layout.BorderPane
import tornadofx.View
import tornadofx.singleAssign

abstract class MainViewNodes: View("Bilibili，干杯~") {
    var rightBox by singleAssign<BorderPane>()
}