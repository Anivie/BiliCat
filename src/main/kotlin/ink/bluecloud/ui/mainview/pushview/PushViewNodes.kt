package ink.bluecloud.ui.mainview.pushview

import javafx.scene.layout.FlowPane
import tornadofx.*

abstract class PushViewNodes: Fragment("Bilibili，干杯~") {
    var pushPane by  singleAssign<FlowPane>()
}