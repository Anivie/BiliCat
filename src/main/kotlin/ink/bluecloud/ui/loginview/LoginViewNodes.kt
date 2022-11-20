package ink.bluecloud.ui.loginview

import javafx.scene.control.Button
import javafx.scene.image.ImageView
import tornadofx.*

abstract class LoginViewNodes: View("登录") {
    var phoneButton by  singleAssign<Button>()
    var accountButton by  singleAssign<Button>()

    var qrCodeBox by  singleAssign<ImageView>()
}