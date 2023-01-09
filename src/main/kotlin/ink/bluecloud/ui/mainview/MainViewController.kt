package ink.bluecloud.ui.mainview

import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.ui.CloudController
import ink.bluecloud.ui.loginview.LoginView
import ink.bluecloud.utils.settingloader.SettingCenter
import ink.bluecloud.utils.settingloader.chaekSettingIsNull
import org.koin.core.annotation.Single
import org.koin.core.component.get

@Single
class MainViewController : CloudController<MainView>() {
    override fun initUi(view: MainView): Unit = view.run {
        if (get<SettingCenter>().chaekSettingIsNull<CookieJson>()) openInternalWindow<LoginView>(closeButton = false)
    }
}