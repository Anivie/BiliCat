package ink.bluecloud.service.clientservice.comments.info.load

import ink.bluecloud.model.networkapi.api.NetWorkResourcesProvider
import ink.bluecloud.model.pojo.comment.info.load.CommentAreaHotPOJO
import ink.bluecloud.service.ClientService
import ink.bluecloud.service.clientservice.comments.info.enums.CommentType
import ink.bluecloud.utils.IDConvert
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.param
import ink.bluecloud.utils.toObjJson
import org.koin.core.annotation.Factory
import org.koin.core.component.get

/**
 * 获取评论区热评
 * @BUILD: Under Repair
 */
@Factory
class CommentAreaHot : ClientService() {
    /**
     * 获取评论区热评
     * @param oid：(对于视频评论来说可以传入BVID或AVID number) 每个type对应一个oid，oid具体是什么见该CommentType的注释中“：”后面的内容
     * @param rid 根回复 rpid
     * @param pageNumber: pn 页码
     * @param type 评论区类型
     * @param pageSize: ps 每页项数（1-49）,默认 20 (最大数为20超过20不返回内容。超过49报错)
     */
    suspend fun getCommentHotInfo(
        oid: String,
        rid: Long = -1,
        pageNumber: Int = 1,
        type: CommentType = CommentType.AV_ID,
        pageSize: Int = 20,
    ): CommentAreaHotPOJO.Root {
        val param = get<NetWorkResourcesProvider>().api.getCommentAreaHot.param {
            it["type"] = type.value.toString()
            if (rid >= 0) it["root"] = rid.toString()
            it["oid"] = if (IDConvert().isBvid(oid)) IDConvert().BvToAvNumber(oid).toString() else oid
            it["ps"] =
                if (pageSize in 1..49) pageSize.toString() else throw IllegalArgumentException("param 'pageSize' must be between 1 and 49")
            it["pn"] = pageNumber.toString()
        }

        logger.debug("API Get CommentAreaHot -> $param")
        return httpClient.getForString(param).toObjJson(CommentAreaHotPOJO.Root::class.java)
    }
}