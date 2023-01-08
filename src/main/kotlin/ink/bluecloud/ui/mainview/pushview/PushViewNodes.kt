package ink.bluecloud.ui.mainview.pushview

import javafx.scene.layout.FlowPane
import tornadofx.Fragment
import tornadofx.singleAssign

abstract class PushViewNodes: Fragment("Bilibili，干杯~") {
    var pushPane by  singleAssign<FlowPane>()
}