package ink.bluecloud.ui.mainview

import ink.bluecloud.ui.mainview.node.ExperienceBar
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import tornadofx.*

abstract class MainViewNodes: View("Bilibili，干杯~") {
    var headView by singleAssign<ImageView>()
    var userName by singleAssign<Label>()
//    var levelLabel by singleAssign<Label>()
    var levelBar by singleAssign<ExperienceBar>()
    var coinLabel by singleAssign<Label>()
}