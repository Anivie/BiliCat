package ink.bluecloud.service.user.relationship

import ink.bluecloud.model.pojo.user.relationship.UserRelationshipUniversallyPOJO
import ink.bluecloud.service.APIResources
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.toObjJson
import org.koin.core.annotation.Factory

/**
 *查询与自己互关的用户明细，可看全部
 */
@Factory
class AccountMutualAttention :APIResources(){
    suspend fun getAccountMutualAttention(): UserRelationshipUniversallyPOJO.Root {
        val api = api(API.getAccountMutualAttention)
        return httpClient.getForString(api.url).toObjJson(UserRelationshipUniversallyPOJO.Root::class.java)
    }
}