package ink.bluecloud.service.clientservice.init

import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.service.ClientService
import ink.bluecloud.service.clientservice.account.cookie.CookieUpdate
import ink.bluecloud.utils.settingloader.loadSetting
import org.koin.core.annotation.Factory

@Factory
class LoadCookie: ClientService() {
    fun load():Boolean {
        return settingCenter.loadSetting<CookieJson>()?.run {
            CookieUpdate().loadCookie(this)
            true
        }?: false
    }
}