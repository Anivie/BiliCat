package ink.bluecloud.ui.mainview

import ink.bluecloud.model.data.account.AccountCard
import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.service.cache.CacheCenter
import ink.bluecloud.service.seeting.SettingCenter
import ink.bluecloud.service.seeting.checkSettingIsNull
import ink.bluecloud.service.user.AccountInfo
import ink.bluecloud.ui.CloudController
import ink.bluecloud.ui.loginview.LoginView
import ink.bluecloud.utils.newIO
import ink.bluecloud.utils.onIO
import ink.bluecloud.utils.onUI
import javafx.scene.image.Image
import org.koin.core.annotation.Single
import org.koin.core.component.get
import tornadofx.*

@Single
class MainViewController : CloudController<MainView>() {
    private lateinit var accountCard: AccountCard

    @Suppress("BlockingMethodInNonBlockingContext")
    override fun initUi(view: MainView): Unit = view.run {
        if (get<SettingCenter>().checkSettingIsNull<CookieJson>()) openInternalWindow<LoginView>(closeButton = false)

        newIO("UserCardInitializer") {
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
                userName.style(true) {
                    textFill = if (accountCard.vip) {
                        c(251,114,153)
                    } else {
                        c(0,174,236)
                    }
                }

                levelBar.level.value = accountCard.level
                levelLabel.text = "Lv:${accountCard.level}"
                coinLabel.text = "\uE7AC:${accountCard.coin}"

                suspensionProFileCard.accountInfo.value = accountCard
            }

            println(accountCard)
        }
    }
}