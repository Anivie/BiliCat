package ink.bluecloud.service.user.relationship

import ink.bluecloud.model.pojo.user.relationship.UserRelationshipUniversallyPOJO
import ink.bluecloud.service.APIResources
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.toObjJson
import org.koin.core.annotation.Factory

/**
 * 查询共同关注明细
 */
@Factory
class UserCommonFollow :APIResources(){

    /**
     * 查询共同关注明细
     * @param mid user's mid
     * @param ps page  size
     * @param pn page number
     */
    suspend fun getCommonFollowers(mid:Long,ps:Int=50,pn:Int=1): UserRelationshipUniversallyPOJO.Root {
        val api = api(API.getCommonFollowers){
            it["vmid"]=mid.toString()
            it["ps"]=ps.toString()
            it["pn"]=pn.toString()
        }

        return httpClient.getForString(api.url).toObjJson(UserRelationshipUniversallyPOJO.Root::class.java)
    }
}