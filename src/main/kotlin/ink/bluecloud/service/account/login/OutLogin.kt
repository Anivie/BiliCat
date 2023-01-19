package ink.bluecloud.service.account.login

import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.service.APIResources
import ink.bluecloud.utils.postForCodePojo
import org.koin.core.annotation.Factory

@Factory
class OutLogin:APIResources() {
    suspend fun outLogin(cookie:CookieJson = httpClient.getCookieStore().toCookies()): Boolean {
        val api = api(API.postOutLogin,APILevel.Post) {
            it["biliCSRF"] = getCsrf()
        }

        return httpClient.postForCodePojo(api.url,api.params).code == 0
    }
}