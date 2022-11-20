package ink.bluecloud.ui.loginview

import com.alibaba.fastjson2.JSONWriter
import ink.bluecloud.cloudtools.cloudnotice.Property.NoticeType
import ink.bluecloud.cloudtools.stageinitializer.TitleBar
import ink.bluecloud.service.clientservice.account.login.LoginService
import ink.bluecloud.service.clientservice.release.ReleaseService
import ink.bluecloud.ui.Controller
import ink.bluecloud.ui.cloudnotice
import javafx.application.Platform
import javafx.scene.image.Image
import org.koin.core.annotation.Single
import org.koin.core.component.get
import tornadofx.*

@Single
class LoginViewController: Controller() {

    override fun View.initUi() {
        this as LoginView

        (root.top as TitleBar).onCloseRequest = {
            get<ReleaseService>().onExit()
            Platform.exit()
        }

        val loginService = get<LoginService>()
        ui {
            qrCodeBox.image = Image(loginService.getCode())
        }
        io {
            val jsonObject = loginService.whenSuccess()
            cloudnotice(NoticeType.Right, "登录成功！")
            println(jsonObject.toJSONString(JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteMapNullValue))
        }
    }
}