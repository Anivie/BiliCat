package ink.bluecloud.service.clientservice.barrage

import ink.bluecloud.model.pojo.barrage.real.Barrage
import ink.bluecloud.model.pojo.barrage.real.Barrages
import ink.bluecloud.model.pojo.barrage.real.toBarrage
import ink.bluecloud.service.ClientService
import ink.bluecloud.utils.getForBytes
import ink.bluecloud.utils.param
import ink.bluecloud.utils.toAvNumber
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import org.koin.core.annotation.Single

/**
 * 获取b站实时弹幕。每6分钟为一个列表，可指定某次列表索引
 * @Building
 */
@Single
class RealTimeBarrage : ClientService() {
    /**
     * @param bvid 视频的BVID （可选）
     * @param cid 视频的CID （必要参数）
     * @param index 实时弹幕分包(可选，默认 1) 每6分钟为一个包
     * @param type 弹幕类型，默认是 1
     */
    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getBarrages(cid: Long, bvid: String = "", index: Int = 1, type: Int = 1): List<Barrage> {
        val param = netWorkResourcesProvider.api.getRealTimeBarrage.param {
            it["type"] = type.toString()
            it["oid"] = cid.toString()
            if (bvid.isNotEmpty()) it["pid"] = bvid.toAvNumber().toString()
            it["segment_index"] = index.toString()
        }
        logger.debug("API Get RealTimeBarrage -> $param")
        val bytes = httpClient.getForBytes(param)
        kotlin.runCatching { ProtoBuf.decodeFromByteArray<Barrages>(bytes).barrages.map { it.toBarrage() } }
            .onFailure {
                logger.error(
                            "An error occurred and the bullet screen package could not be parsed: \n" +
                            "------------------------------------------------------------------------\n" +
                            " ${String(bytes)}\n" +
                            "------------------------------------------------------------------------\n\n", it
                )
            }
            .onSuccess { return it }
        return ArrayList()
    }
}