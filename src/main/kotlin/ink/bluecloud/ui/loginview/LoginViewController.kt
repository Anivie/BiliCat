package ink.bluecloud.ui.loginview

import com.alibaba.fastjson2.JSONWriter
import ink.bluecloud.cloudtools.cloudnotice.Property.NoticeType
import ink.bluecloud.cloudtools.stageinitializer.TitleBar
import ink.bluecloud.network.http.HttpClient
import ink.bluecloud.service.clientservice.account.login.LoginService
import ink.bluecloud.ui.Controller
import ink.bluecloud.ui.cloudnotice
import javafx.application.Platform
import javafx.scene.image.Image
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import org.koin.core.component.get
import org.koin.core.component.inject

@Single
class LoginViewController: Controller<LoginView>() {
    private val loginService by inject<LoginService>()

    override fun initUi(view: LoginView) = view.run {
        (root.top as TitleBar).onCloseRequest = {
            get<HttpClient>().close()
            Platform.exit()
        }

        io {
            withContext(uiContext) {
                qrCodeBox.image = Image(loginService.getCode())
            }

            val jsonObject = loginService.whenSuccess()
            cloudnotice(NoticeType.Right, "登录成功！")
            println(jsonObject.toJSONString(JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteMapNullValue))
        }
    }
}