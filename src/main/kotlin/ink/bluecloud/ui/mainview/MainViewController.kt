package ink.bluecloud.ui.mainview

import ink.bluecloud.ui.CloudController
import org.koin.core.annotation.Single

@Single
class MainViewController : CloudController<MainView>() {
    override fun initUi(view: MainView) = view.run {

    }
}