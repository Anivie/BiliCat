package ink.bluecloud.service.user.relationship

import ink.bluecloud.model.pojo.user.relationship.UserAttentionPOJO
import ink.bluecloud.service.APIResources
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.toObjJson
import org.koin.core.annotation.Factory

@Factory
class UserAttention : APIResources() {

    suspend fun getAttention(
        mid: Long,
        timeSort: Boolean = true,
        pageSize: Int = 50,
        pageNumber: Int = 1,
    ): UserAttentionPOJO.Root {
        val api = api(API.getUserAttention) {
            it["vmid"] = mid.toString()
            it["order_type"] = if (timeSort) "" else "attention"
            it["ps"] = pageSize.toString()
            it["pn"] = pageNumber.toString()
        }

        return httpClient.getForString(api.url).toObjJson(UserAttentionPOJO.Root::class.java)
    }


    suspend fun getAttentionV2(
        mid: Long,
        timeSort: Boolean = true,
        pageSize: Int = 50,
        pageNumber: Int = 1,
    ): UserAttentionPOJO.Root {
        val api = api(API.getUserAttentionV2) {
            it["vmid"] = mid.toString()
            it["order_type"] = if (timeSort) "" else "attention"
            it["ps"] = pageSize.toString()
            it["pn"] = pageNumber.toString()
        }

        return httpClient.getForString(api.url).toObjJson(UserAttentionPOJO.Root::class.java)
    }
}