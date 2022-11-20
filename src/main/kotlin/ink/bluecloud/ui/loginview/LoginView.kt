package ink.bluecloud.ui.loginview

import ink.bluecloud.cloudtools.stageinitializer.TitleBar
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import tornadofx.*

class LoginView: LoginViewNodes() {

    override val root = borderpane {
        top = TitleBar("登录", primaryStage)

        center = getQrCodeBox()
/*
        bottom = hbox(10,alignment = Pos.CENTER) {
            phoneButton = button("手机登录")
            accountButton = button("账号登录")

            paddingBottom = 30
        }
*/

        style = "-fx-background-image: url(ui/loginview/background.png);" +
                "-fx-background-size: cover;"


        prefWidth = 450.0
        prefHeight = 450.0
    }

    private fun getPasswordBox() = gridpane {
        row {
            label("用户名:")
            textfield()
        }
        row {
            label("密码:")
            textfield()
        }
        row {
            checkbox("记住密码?")
        }

        hgap = 5.0
        vgap = 10.0
        alignment = Pos.CENTER
        hgrow = Priority.ALWAYS
    }

    private fun getQrCodeBox() = hbox(alignment = Pos.CENTER) {
        qrCodeBox = imageview()
    }

    override fun onDock() {
        find<LoginViewController>().run {
            initUi()
        }
    }
}