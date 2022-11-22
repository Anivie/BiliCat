package ink.bluecloud.ui.mainview

import ink.bluecloud.ui.Controller
import javafx.geometry.Pos
import org.koin.core.annotation.Single
import tornadofx.*

@Single
class MainViewController : Controller<MainView>() {
    override fun initUi(view: MainView) = view.run {
        rightBox.run {
            top = hbox(10, Pos.CENTER) {

            }
        }
    }
}