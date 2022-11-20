package ink.bluecloud.service.clientservice.account.cookie

import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.network.http.CookieManager
import ink.bluecloud.service.ClientService
import org.koin.core.annotation.Factory

@Factory
class CookieUpdate : ClientService() {

    fun loadCookie(cookie: CookieJson) {
        httpClient.getCookieStore().parseTo(cookie)
    }

    fun getCookieStore(): CookieManager {
        return httpClient.getCookieStore()
    }
}