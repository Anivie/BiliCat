package ink.bluecloud.service.clientservice.comments.info.load

import ink.bluecloud.model.pojo.comment.info.load.CommentAreaPageLoadPOJO
import ink.bluecloud.service.clientservice.APIResources
import ink.bluecloud.service.clientservice.comments.info.enums.CommentType
import ink.bluecloud.service.clientservice.comments.info.enums.PageCommentAreaSort
import ink.bluecloud.service.clientservice.video.id.IDConvert
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.toObjJson
import org.koin.core.annotation.Factory
import kotlin.collections.set

/**
 * 评论区翻页加载
 */
@Factory
class CommentAreaPageLoad : APIResources() {

    /**
     * 评论区翻页加载
     * @param oid：(对于视频评论来说可以传入BVID或AVID number) 每个type对应一个oid，oid具体是什么见该CommentType的注释中“：”后面的内容
     * @param sort 排序方式：按时间、按点赞数、按回复数
     * @param pageNumber: pn 页码
     * @param type 评论区类型
     * @param pageSize: ps 每页项数（1-49）,默认49
     * @param notHot 是否不显示热评：true(1)不显示、false(0)显示(默认)
     */
    suspend fun getCommentAreaInfo(
        oid: String,
        sort: PageCommentAreaSort = PageCommentAreaSort.TIME,
        pageNumber: Int = 1,
        type: CommentType = CommentType.AV_ID,
        pageSize: Int = 49,
        notHot: Boolean = false,
    ): CommentAreaPageLoadPOJO.Root {
        val api = api(API.getCommentAreaPageLoad){
            it["type"] = type.value.toString()
            it["sort"] = sort.value.toString()
            it["oid"] = if (IDConvert().isBvid(oid)) IDConvert().BvToAvNumber(oid).toString() else oid
            it["nothot"] = if (notHot) "1" else "0"
            it["ps"] =
                if (pageSize in 1..49) pageSize.toString() else throw IllegalArgumentException("param 'pageSize' must be between 1 and 49")
            it["pn"] = pageNumber.toString()
        }
        return httpClient.getForString(api.url).toObjJson(CommentAreaPageLoadPOJO.Root::class.java)
    }
}