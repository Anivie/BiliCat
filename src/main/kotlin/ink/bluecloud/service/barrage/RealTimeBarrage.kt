package ink.bluecloud.service.barrage

import ink.bluecloud.model.pojo.barrage.real.Barrage
import ink.bluecloud.service.APIResources
import ink.bluecloud.service.video.id.IDConvert
import ink.bluecloud.utils.getForBytes
import org.koin.core.annotation.Single

/**
 * 获取b站实时弹幕。每6分钟为一个列表，可指定某次列表索引
 * @Building
 */
@Single
class RealTimeBarrage : APIResources() {
    /**
     * @param bvid 视频的BVID （可选）
     * @param cid 视频的CID （必要参数）
     * @param index 实时弹幕分包(可选，默认 1) 每6分钟为一个包
     * @param type 弹幕类型，默认是 1
     */
    suspend fun getBarrages(cid: Long, bvid: String = "", index: Int = 1, type: Int = 1): List<Barrage> {
        val api = api(API.getRealTimeBarrage){
            it["type"] = type.toString()
            it["oid"] = cid.toString()
            if (bvid.isNotEmpty()) it["pid"] = IDConvert().BvToAvNumber(bvid).toString()
            it["segment_index"] = index.toString()
        }
        return BarrageHandler(cid).handle(httpClient.getForBytes(api.url))
    }
}