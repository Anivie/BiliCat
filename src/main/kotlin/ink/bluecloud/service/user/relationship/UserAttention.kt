package ink.bluecloud.service.user.relationship


import ink.bluecloud.model.pojo.user.relationship.UserRelationshipUniversallyPOJO
import ink.bluecloud.service.APIResources
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.toObjJson
import org.koin.core.annotation.Factory

/**
 * 查询用户关注明细
 */
@Factory
class UserAttention : APIResources() {
    /**
     * 搜索关注的人
     * @param mid 目标用户mid
     * @param name 搜索关键词
     */
    suspend fun getUserFollow(
        mid: Long,
        name: String,
        pageSize: Int = 50,
        pageNumber: Int = 1,
    ): UserRelationshipUniversallyPOJO.Root {
        val api = api(API.getUserFollow) {
            it["vmid"] = mid.toString()
            it["name"] = name
            it["ps"] = pageSize.toString()
            it["pn"] = pageNumber.toString()
        }

        return httpClient.getForString(api.url).toObjJson(UserRelationshipUniversallyPOJO.Root::class.java)
    }


    /**
     * 查询用户关注明细
     */
    suspend fun getAttention(
        mid: Long,
        timeSort: Boolean = true,
        pageSize: Int = 50,
        pageNumber: Int = 1,
    ): UserRelationshipUniversallyPOJO.Root {
        val api = api(API.getUserAttention) {
            it["vmid"] = mid.toString()
            it["order_type"] = if (timeSort) "" else "attention"
            it["ps"] = pageSize.toString()
            it["pn"] = pageNumber.toString()
        }

        return httpClient.getForString(api.url).toObjJson(UserRelationshipUniversallyPOJO.Root::class.java)
    }


    /**
     * 登录可看自己全部，其他用户仅可查看前5页，可以获取已设置可见性隐私的关注列表
     */
    suspend fun getAttentionV2(
        mid: Long,
        timeSort: Boolean = true,
        pageSize: Int = 50,
        pageNumber: Int = 1,
    ): UserRelationshipUniversallyPOJO.Root {
        val api = api(API.getUserAttentionV2) {
            it["vmid"] = mid.toString()
            it["order_type"] = if (timeSort) "" else "attention"
            it["ps"] = pageSize.toString()
            it["pn"] = pageNumber.toString()
        }

        return httpClient.getForString(api.url).toObjJson(UserRelationshipUniversallyPOJO.Root::class.java)
    }
}