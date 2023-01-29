package ink.bluecloud.service.user.relationship

import ink.bluecloud.model.pojo.user.relationship.UserRelationshipUniversallyPOJO
import ink.bluecloud.service.APIResources
import ink.bluecloud.service.user.AccountInfo
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.toObjJson
import org.koin.core.annotation.Factory

/**
 * 查询用户粉丝
 */
@Factory
class UserFans : APIResources() {
    /**
     * 查询用户粉丝明细
     * @param mid  目标用户mid: 登录可看自己前1000名，其他用户可查看前250名（网页端请求时ps为20，所以直接查看只能看到前100名）
     * @param ps 每页项数
     * @param pn 页码
     */
    suspend fun getFans(mid: Long, pn: Int = 1, ps: Int = 50): UserRelationshipUniversallyPOJO.Root {
        val api = api(API.getUserFans) {
            it["vmid"] = mid.toString()
            it["pn"] = pn.toString()
            it["ps"] = ps.toString()
        }
        return httpClient.getForString(api.url).toObjJson(UserRelationshipUniversallyPOJO.Root::class.java)
    }
}