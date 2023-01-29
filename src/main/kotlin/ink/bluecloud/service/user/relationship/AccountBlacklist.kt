package ink.bluecloud.service.user.relationship

import ink.bluecloud.model.pojo.user.relationship.UserRelationshipUniversallyPOJO
import ink.bluecloud.service.APIResources
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.toObjJson
import org.koin.core.annotation.Factory

@Factory
class AccountBlacklist:APIResources() {
    suspend fun getAccountBlacklist(ps:Int=50,pn:Int=1): UserRelationshipUniversallyPOJO.Root {
        val api = api(API.getAccountBlacklist) {
            it["ps"] = ps.toString()
            it["pn"] = pn.toString()
        }
        return httpClient.getForString(api.url).toObjJson(UserRelationshipUniversallyPOJO.Root::class.java)
    }
}