package ink.bluecloud.ui.mainview.homeview

import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import tornadofx.*

abstract class HomeViewNodes: View() {
    var rootBox by singleAssign<VBox>()
    var secondBox by singleAssign<HBox>()
}