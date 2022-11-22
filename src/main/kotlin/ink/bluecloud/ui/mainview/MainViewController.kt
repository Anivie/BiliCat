package ink.bluecloud.ui.mainview

import ink.bluecloud.ui.Controller
import org.koin.core.annotation.Single

@Single
class MainViewController : Controller<MainView>() {
    override fun initUi(view: MainView) = view.run {

    }
}