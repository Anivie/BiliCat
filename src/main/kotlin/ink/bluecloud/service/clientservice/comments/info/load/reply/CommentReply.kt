package ink.bluecloud.service.clientservice.comments.info.load.reply

import ink.bluecloud.model.pojo.comment.info.load.reply.CommentReplyPOJO
import ink.bluecloud.service.clientservice.APIResources
import ink.bluecloud.service.clientservice.comments.info.enums.CommentType
import ink.bluecloud.service.clientservice.video.id.IDConvert
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.toObjJson
import org.koin.core.annotation.Factory
import kotlin.collections.set

/**
 * 获取指定评论的回复
 */
@Factory
class CommentReply: APIResources() {

    /**
     * 获取指定评论的回复
     * @param oid：(对于视频评论来说可以传入BVID或AVID number) 每个type对应一个oid，oid具体是什么见该CommentType的注释中“：”后面的内容
     * @param rid 根回复 rpid
     * @param pageNumber: pn 页码
     * @param type 评论区类型
     * @param pageSize: ps 每页项数（1-49）,默认 20 (最大数为20超过20不返回内容。超过49报错)
     */
    suspend fun getCommentReplyInfo(
        oid: String,
        rid: Long,
        pageNumber: Int = 1,
        type: CommentType = CommentType.AV_ID,
        pageSize: Int = 20,
    ): CommentReplyPOJO.Root {
        val api = api(API.getCommentReply){
            it["type"] = type.value.toString()
            it["root"] = rid.toString()
            it["oid"] = if (IDConvert().isBvid(oid)) IDConvert().BvToAvNumber(oid).toString() else oid
            it["ps"] =
                if (pageSize in 1..49) pageSize.toString() else throw IllegalArgumentException("param 'pageSize' must be between 1 and 49")
            it["pn"] = pageNumber.toString()
        }
        return httpClient.getForString(api.url).toObjJson(CommentReplyPOJO.Root::class.java)
    }
}