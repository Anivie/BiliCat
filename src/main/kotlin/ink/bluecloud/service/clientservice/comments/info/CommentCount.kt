package ink.bluecloud.service.clientservice.comments.info

import com.alibaba.fastjson2.JSONObject
import ink.bluecloud.model.networkapi.api.NetWorkResourcesProvider
import ink.bluecloud.service.ClientService
import ink.bluecloud.service.clientservice.comments.info.enums.CommentType
import ink.bluecloud.service.clientservice.video.id.IDConvert
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.param
import org.koin.core.annotation.Factory
import org.koin.core.component.get

/**
 * 获取评论区评论总数
 */
@Factory
class CommentCount : ClientService() {

    /**
     * 获取评论区评论总数
     * @param oid：(对于视频评论来说可以传入BVID或AVID number) 每个type对应一个oid，oid具体是什么见该CommentType的注释中“：”后面的内容
     * @param type 评论区类型
     */
    suspend fun getCommentCount(
        oid: String,
        type: CommentType = CommentType.AV_ID,
    ): Long {
        val param = get<NetWorkResourcesProvider>().api.getCommentAreaCount.param {
            it["type"] = type.value.toString()
            it["oid"] = if (IDConvert().isBvid(oid)) IDConvert().BvToAvNumber(oid).toString() else oid
        }

        logger.info("API Get CommentCount -> $param")
        return JSONObject.parseObject(httpClient.getForString(param)).getJSONObject("data").getLong("count")
    }
}