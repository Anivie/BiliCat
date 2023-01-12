package ink.bluecloud.service.clientservice.barrage.operation

import com.alibaba.fastjson2.JSONObject
import ink.bluecloud.model.pojo.barrage.real.Barrage
import ink.bluecloud.model.pojo.barrage.real.BarragePool
import ink.bluecloud.service.clientservice.APIResources
import ink.bluecloud.service.clientservice.account.cookie.CookieUpdate
import ink.bluecloud.utils.*
import org.koin.core.annotation.Single

@Single
class SendBarrage : APIResources() {
    /**
     * 发送弹幕
     * @param barrage 弹幕
     * @param type 弹幕类型
     * @return 弹幕的唯一ID
     */
    suspend fun send(bvid: String, barrage: Barrage, type: Int = 1): Long {
        val api = api(API.postSendBarret, APILevel.Post) {
            it["type"] = type.toString()
            it["oid"] = barrage.cid.toString()
            it["msg"] = barrage.content.toString()
            it["bvid"] = bvid
            it["progress"] =
                if (barrage.progress == null) System.currentTimeMillis().toString() else barrage.progress?.toMillis()
                    .toString() //单位为毫秒
            it["color"] = barrage.color.color.toString()
            it["fontsize"] = barrage.fontSize.value.toString()
            it["pool"] =
                if (barrage.pool == null) BarragePool.Default.value.toString() else barrage.pool?.value.toString()
            it["mode"] = barrage.type.value.toString()
            it["rnd"] = (barrage.sendTime * 1000000).toString()
            it["csrf"] = getCsrf()
        }
        val data = httpClient.postForCodePojo(api.url, api.params).data
        return JSONObject.parseObject(data).getLong("dmid")
    }
}