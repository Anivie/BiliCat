package ink.bluecloud.service.clientservice.account.cookie

import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.network.http.CookieManager
import ink.bluecloud.service.ClientService
import org.jsoup.HttpStatusException
import org.koin.core.annotation.Factory

@Factory
class CookieUpdate : ClientService() {

    fun loadCookie(cookie: CookieJson){
        httpClient.getCookieStore().parseTo(cookie)
        updateCookie()
    }

    fun loadCookie(cookie: String){
        httpClient.getCookieStore().parseAllTo(cookie)
        updateCookie()
    }

    fun getCookieStore(): CookieManager {
        return httpClient.getCookieStore()
    }

    fun getCsrf(): String {
        return getCookieStore().toCookies().csrf
    }

    /**
     * 更新 Cookie (不等待更新，但在未来更新完毕)
     */
    private fun updateCookie() = httpClient.getFor(netWorkResourcesProvider.api.getBili) {
        use {
            logger.debug("System-services Get Cookie-Update -> ${netWorkResourcesProvider.api.getBili}")
            if (it.code != 200) throw HttpStatusException(
                "http status code is not 200",
                code,
                netWorkResourcesProvider.api.getBili.toString()
            )
        }
    }
}