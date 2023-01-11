package ink.bluecloud.service.clientservice.comments.info.load

import ink.bluecloud.model.networkapi.api.NetWorkResourcesProvider
import ink.bluecloud.model.pojo.comment.info.load.CommentAreaLazyLoadPOJO
import ink.bluecloud.service.ClientService
import ink.bluecloud.service.clientservice.comments.info.enums.CommentType
import ink.bluecloud.service.clientservice.comments.info.enums.LazyCommentAreaSort
import ink.bluecloud.service.clientservice.video.id.IDConvert
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.param
import ink.bluecloud.utils.toObjJson
import org.koin.core.annotation.Factory
import org.koin.core.component.get

/**
 * 评论区懒加载
 */
@Factory
class CommentAreaLazyLoad : ClientService() {

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
        val param = get<NetWorkResourcesProvider>().api.getCommentLazyPageLoad.param {
            it["type"] = type.value.toString()
            it["mode"] = sort.value.toString()
            it["oid"] = if (IDConvert().isBvid(oid)) IDConvert().BvToAvNumber(oid).toString() else oid
            it["ps"] =
                if (pageSize in 1..30) pageSize.toString() else throw IllegalArgumentException("param 'pageSize' must be between 1 and 30")
            it["next"] = pageNumber.toString()
        }

        logger.info("API Get CommentAreaLazyLoad -> $param")
        return httpClient.getForString(param).toObjJson(CommentAreaLazyLoadPOJO.Root::class.java)
    }
}