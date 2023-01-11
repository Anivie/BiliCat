package ink.bluecloud.service.clientservice.barrage.operation

import ink.bluecloud.service.clientservice.APIResources
import ink.bluecloud.utils.postForCodePojo
import org.koin.core.annotation.Single

/**
 * 撤销弹幕
 */
@Single
class CancelBarrage: APIResources() {
    suspend fun cancel(dmid:Long, cid:Long){
        val api = api(API.postCancelBarret,APILevel.Post){
            it["csrf"] = getCsrf()
            it["cid"] = cid.toString()
            it["dmid"] = dmid.toString()
        }
        val msg = httpClient.postForCodePojo(api.url, api.params).message
        println(msg)
    }
}