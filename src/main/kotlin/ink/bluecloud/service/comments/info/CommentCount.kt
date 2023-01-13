package ink.bluecloud.service.comments.info

import com.alibaba.fastjson2.JSONObject
import ink.bluecloud.service.APIResources
import ink.bluecloud.service.comments.info.enums.CommentType
import ink.bluecloud.service.video.id.IDConvert
import ink.bluecloud.utils.getForString
import org.koin.core.annotation.Factory
import kotlin.collections.set

/**
 * 获取评论区评论总数
 */
@Factory
class CommentCount : APIResources() {

    /**
     * 获取评论区评论总数
     * @param oid：(对于视频评论来说可以传入BVID或AVID number) 每个type对应一个oid，oid具体是什么见该CommentType的注释中“：”后面的内容
     * @param type 评论区类型
     */
    suspend fun getCommentCount(
        oid: String,
        type: CommentType = CommentType.AV_ID,
    ): Long {
        val api = api(API.getCommentAreaCount){
            it["type"] = type.value.toString()
            it["oid"] = if (IDConvert().isBvid(oid)) IDConvert().BvToAvNumber(oid).toString() else oid
        }
        return JSONObject.parseObject(httpClient.getForString(api.url)).getJSONObject("data").getLong("count")
    }
}