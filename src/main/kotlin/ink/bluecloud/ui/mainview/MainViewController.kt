package ink.bluecloud.ui.mainview

import ink.bluecloud.ui.Controller
import javafx.geometry.Pos
import org.koin.core.annotation.Single
import tornadofx.*

@Single
class MainViewController : Controller() {

    override fun View.initUi() = (this as MainView).run {
        rightBox.run {
            top = hbox(10, Pos.CENTER) {
//                dispatcher.service<UserServiceProvider, AccountInfo> {
/*
                    getAccountInfo {
                        imageview(Image(it.head)) {
                            clip = Rectangle(50.0, 50.0).apply {
                                arcWidth = 25.0
                                arcHeight = 25.0
                            }
                            fitWidth = 50.0
                            fitHeight = 50.0
                        }

                        label(it.name) {
                            style {
                                fontFamily = "HarmonyOS_Sans_SC_Bold"
                                fontSize = 20.px
                            }
                        }
                    }
*/
//                }
            }
        }
    }
}