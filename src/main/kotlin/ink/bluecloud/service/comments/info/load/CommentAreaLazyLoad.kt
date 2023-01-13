package ink.bluecloud.service.comments.info.load

import ink.bluecloud.model.pojo.comment.info.load.CommentAreaLazyLoadPOJO
import ink.bluecloud.service.APIResources
import ink.bluecloud.service.comments.info.enums.CommentType
import ink.bluecloud.service.comments.info.enums.LazyCommentAreaSort
import ink.bluecloud.service.video.id.IDConvert
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.toObjJson
import org.koin.core.annotation.Factory
import kotlin.collections.set

/**
 * 评论区懒加载
 */
@Factory
class CommentAreaLazyLoad : APIResources() {

    /**
     * 评论区懒加载
     * @param oid：(对于视频评论来说可以传入BVID或AVID number) 每个type对应一个oid，oid具体是什么见该CommentType的注释中“：”后面的内容
     * @param sort 排序方式：按热度、按时间
     * @param pageNumber: pn 页码： 按热度时热度顺序页码（0 为第一页）,按时间时：时间倒序楼层号
     * 默认为 0
     * @param type 评论区类型
     * @param pageSize: ps 每页项数（1-30）,默认20
     */
    suspend fun getCommentAreaInfo(
        oid: String,
        sort: LazyCommentAreaSort = LazyCommentAreaSort.DEFAULT_HOT,
        pageNumber: Int = 0,
        type: CommentType = CommentType.AV_ID,
        pageSize: Int = 20,
    ): CommentAreaLazyLoadPOJO.Root {
        val api = api(API.getCommentLazyPageLoad){
            it["type"] = type.value.toString()
            it["mode"] = sort.value.toString()
            it["oid"] = if (IDConvert().isBvid(oid)) IDConvert().BvToAvNumber(oid).toString() else oid
            it["ps"] =
                if (pageSize in 1..30) pageSize.toString() else throw IllegalArgumentException("param 'pageSize' must be between 1 and 30")
            it["next"] = pageNumber.toString()
        }
        return httpClient.getForString(api.url).toObjJson(CommentAreaLazyLoadPOJO.Root::class.java)
    }
}