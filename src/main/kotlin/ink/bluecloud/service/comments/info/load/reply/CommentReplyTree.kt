package ink.bluecloud.service.comments.info.load.reply

import ink.bluecloud.model.pojo.comment.info.load.reply.CommentReplyTreePOJO
import ink.bluecloud.service.APIResources
import ink.bluecloud.service.comments.info.enums.CommentType
import ink.bluecloud.service.video.id.IDConvert
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.toObjJson
import org.koin.core.annotation.Factory
import kotlin.collections.set

/**
 * ！不推荐
 * 获取指定评论对话树
 */
@Factory
class CommentReplyTree : APIResources() {

    /**
     * 获取指定评论对话树
     * @param oid：(对于视频评论来说可以传入BVID或AVID number) 每个type对应一个oid，oid具体是什么见该CommentType的注释中“：”后面的内容
     * @param rid 根回复 rpid
     * @param dialog 对话树根 rpid
     * @param type 评论区类型
     * @param pageSize: ps 每页最大项数
     */
    suspend fun getCommentReplyTreeInfo(
        oid: String,
        rid: Long,
        dialog: Long,
        type: CommentType = CommentType.AV_ID,
        pageSize: Int = 20,
    ): CommentReplyTreePOJO.Root {
        val api = api(API.getCommentReplyTree){
            it["type"] = type.value.toString()
            it["root"] = rid.toString()
            it["dialog"] = dialog.toString()
            it["oid"] = if (IDConvert().isBvid(oid)) IDConvert().BvToAvNumber(oid).toString() else oid
            it["size"] = pageSize.toString()
//                if (pageSize in 1..49) pageSize.toString() else throw IllegalArgumentException("param 'pageSize' must be between 1 and 49")

        }
        return httpClient.getForString(api.url).toObjJson(CommentReplyTreePOJO.Root::class.java)
    }
}