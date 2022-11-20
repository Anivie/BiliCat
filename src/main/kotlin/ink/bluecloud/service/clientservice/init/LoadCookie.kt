package ink.bluecloud.service.clientservice.init

import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.service.ClientService
import ink.bluecloud.utils.settingloader.readSettingOnly
import org.koin.core.annotation.Factory

@Factory
class LoadCookie: ClientService() {
    fun load() {
        httpClient.getCookieStore().parseTo(settingCenter.readSettingOnly<CookieJson>()?: throw IllegalArgumentException())
    }
}