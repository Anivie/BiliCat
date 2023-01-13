package ink.bluecloud.service.barrage.operation

import com.alibaba.fastjson2.JSONObject
import ink.bluecloud.model.pojo.barrage.real.Barrage
import ink.bluecloud.model.pojo.barrage.real.BarragePool
import ink.bluecloud.service.APIResources
import ink.bluecloud.utils.postForCodePojo
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
            it["msg"] = barrage.content
            it["bvid"] = bvid
            it["progress"] = (barrage.progress?.toMillis() ?: System.currentTimeMillis()).toString()//单位为毫秒
            it["color"] = barrage.color.color.toString()
            it["fontsize"] = barrage.fontSize.value.toString()
            it["pool"] = (barrage.pool?.value ?: BarragePool.Default.value).toString()
            it["mode"] = barrage.type.value.toString()
            it["rnd"] = (barrage.sendTime * 1000000).toString()
            it["csrf"] = getCsrf()
        }
        val data = httpClient.postForCodePojo(api.url, api.params).data
        return JSONObject.parseObject(data).getLong("dmid")
    }
}