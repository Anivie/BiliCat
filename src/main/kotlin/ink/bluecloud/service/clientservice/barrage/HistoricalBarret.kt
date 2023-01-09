package ink.bluecloud.service.clientservice.barrage

import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.toList
import ink.bluecloud.model.pojo.barrage.real.Barrage
import ink.bluecloud.model.pojo.barrage.real.Barrages
import ink.bluecloud.model.pojo.barrage.real.toBarrage
import ink.bluecloud.service.ClientService
import ink.bluecloud.utils.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import org.koin.core.annotation.Factory
import java.util.*

@Factory
class HistoricalBarret : ClientService() {


    /**
     * 获取某一天的历史弹幕
     * @param cid 视频的CID （必要参数）
     * @param date 弹幕日期 YYYY-MM-DD
     * @param type 弹幕类型，默认是 1
     */
    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getHistoricalBarret(cid: Long, date: String = Date().format(), type: Int = 1): List<Barrage> {
        val param = netWorkResourcesProvider.api.getHistoricalBarret.param {
            it["type"] = type.toString()
            it["oid"] = cid.toString()
            it["date"] =
                if (date.isDate()) date else throw IllegalArgumentException("The date(${date}) is wrong. The Date must be YYYY-MM-DD")
        }
        logger.debug("API Get HistoricalBarret -> $param")
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


    /**
     * 获取某个月的日期列表
     * @param cid 视频的CID （必要参数）
     * @param date 弹幕日期 YYYY-MM-DD
     * @param type 弹幕类型，默认是 1
     */
    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getHistoricalBarretDate(
        cid: Long,
        date: String = Date().format("YYYY-MM"),
        type: Int = 1,
    ): List<String> {
        val param = netWorkResourcesProvider.api.getHistoricalBarretDate.param {
            it["type"] = type.toString()
            it["oid"] = cid.toString()
            it["month"] =
                if (date.isDate("YYYY-MM")) date else throw IllegalArgumentException("The date(${date}) is wrong. The Date must be YYYY-MM")
        }
        logger.debug("API Get HistoricalBarretDate -> $param")
        val data = JSONObject.parseObject(httpClient.getForString(param)).getJSONArray("data") ?: return ArrayList()
        return data.toList<String>()
    }


    /**
     * 获取该视频所有的日期列表
     * @param cid 视频的CID （必要参数）
     * @param type 弹幕类型，默认是 1
     */
    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getHistoricalBarretDateAll(cid: Long, type: Int = 1): List<String> {
        val list = ArrayList<String>()
        var date = Date()
        while (true) {
            val barretDate = getHistoricalBarretDate(cid, date.format("YYYY-MM"),type)
            list.addAll(barretDate)
            date = date.add(-1)
            if (barretDate.isEmpty()) break
        }
        return list
    }
}