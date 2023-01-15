package ink.bluecloud.ui.mainview

import ink.bluecloud.model.data.account.AccountCard
import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.service.cache.CacheCenter
import ink.bluecloud.service.seeting.SettingCenter
import ink.bluecloud.service.seeting.checkSettingIsNull
import ink.bluecloud.service.user.AccountInfo
import ink.bluecloud.ui.CloudController
import ink.bluecloud.ui.loginview.LoginView
import ink.bluecloud.utils.io
import ink.bluecloud.utils.onIO
import ink.bluecloud.utils.onUI
import javafx.scene.image.Image
import org.koin.core.annotation.Single
import org.koin.core.component.get

@Single
class MainViewController : CloudController<MainView>() {
    private lateinit var accountCard: AccountCard

//    @Suppress("BlockingMethodInNonBlockingContext")
    override fun initUi(view: MainView): Unit = view.run {
        if (get<SettingCenter>().checkSettingIsNull<CookieJson>()) openInternalWindow<LoginView>(closeButton = false)

        io("UserCardInitializer") {
            accountCard = get<AccountInfo>().getAccountInfo()

            get<CacheCenter>().loadCache("head") {
                onIO {
                    accountCard.head.await().readAllBytes()
                }
            }.run {
                headView.image = Image(this.inputStream())
            }

            onUI {
                userName.text = accountCard.name
                /*if (accountCard.vip) userName.style(true) {
                    textFill = Color.RED
                }*/
//                levelLabel.text = "level:${accountCard.level}"
//                coinLabel.text = "coin:${accountCard.coin}"
            }

            println(accountCard)
        }
    }
}