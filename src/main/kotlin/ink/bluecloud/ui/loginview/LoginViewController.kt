package ink.bluecloud.ui.loginview

import com.alibaba.fastjson2.JSONWriter
import ink.bluecloud.cloudtools.cloudnotice.Property.NoticeType
import ink.bluecloud.service.clientservice.account.login.LoginService
import ink.bluecloud.ui.CloudController
import ink.bluecloud.utils.pushservice.PushServiceimpl
import ink.bluecloud.utils.pushservice.makeNotice
import javafx.scene.image.Image
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import org.koin.core.component.get
import org.koin.core.component.inject

@Single
class LoginViewController: CloudController<LoginView>() {
    private val loginService by inject<LoginService>()

    override fun initUi(view: LoginView) = view.run {
        io {
            withContext(uiContext) {
                qrCodeBox.image = Image(loginService.getCode())
            }

            val jsonObject = loginService.whenSuccess()
            withContext(uiContext) {
                get<PushServiceimpl>().makeNotice(NoticeType.Right,"登录成功！")
                close()
            }
            println(jsonObject.toJSONString(JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteMapNullValue))
        }
    }
}