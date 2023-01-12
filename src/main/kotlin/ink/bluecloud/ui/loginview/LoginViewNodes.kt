package ink.bluecloud.ui.loginview

import javafx.scene.image.ImageView
import tornadofx.*

abstract class LoginViewNodes: View("登录") {
    var qrCodeBox by  singleAssign<ImageView>()
}