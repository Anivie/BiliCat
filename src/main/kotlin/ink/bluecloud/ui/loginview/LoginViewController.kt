package ink.bluecloud.ui.loginview

import ink.bluecloud.cloudtools.cloudnotice.Property.NoticeType
import ink.bluecloud.service.clientservice.account.login.LoginService
import ink.bluecloud.service.clientservice.push.PushServiceimpl
import ink.bluecloud.service.clientservice.push.makeNotice
import ink.bluecloud.ui.CloudController
import ink.bluecloud.ui.mainview.MainView
import ink.bluecloud.utils.io
import ink.bluecloud.utils.onUI
import javafx.scene.image.Image
import javafx.scene.layout.StackPane
import org.koin.core.annotation.Single
import org.koin.core.component.get
import org.koin.core.component.inject

@Single
class LoginViewController: CloudController<LoginView>() {
    private val loginService by inject<LoginService>()

    override fun initUi(view: LoginView) = view.run {
        io {
            onUI {
                qrCodeBox.image = Image(loginService.getCode())
            }

            loginService.whenSuccess()
            onUI {
                get<PushServiceimpl>().makeNotice(NoticeType.Right,"登录成功！")
                (primaryStage.scene.root as StackPane).children[0] = tornadofx.find<MainView>().root
            }
        }
    }
}