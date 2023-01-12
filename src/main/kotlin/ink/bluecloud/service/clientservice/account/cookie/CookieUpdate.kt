package ink.bluecloud.service.clientservice.account.cookie

import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.network.http.CookieManager
import ink.bluecloud.service.clientservice.APIResources
import org.jsoup.HttpStatusException
import org.koin.core.annotation.Factory

@Factory
class CookieUpdate : APIResources() {

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

    /**
     * 更新 Cookie (不等待更新，但在未来更新完毕)
     */
    private fun updateCookie() = httpClient.getFor(API.getBili) {
        use {
            logger.info("System-services Get Cookie-Update -> ${API.getBili}")
            if (it.code != 200) throw HttpStatusException(
                "http status code is not 200",
                code,
                netWorkResourcesProvider.api.getBili.toString()
            )
        }
    }
}