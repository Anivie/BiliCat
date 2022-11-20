package ink.bluecloud.ui.mainview

import javafx.scene.layout.BorderPane
import tornadofx.*

abstract class MainViewNodes: View("Bilibili，干杯~") {
    var rightBox by singleAssign<BorderPane>()
}