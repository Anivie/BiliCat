package ink.bluecloud.model.pojo.comment.info.load

import ink.bluecloud.model.pojo.comment.info.ReplyRoot

class CommentAreaHotPOJO {
    data class Root(
        /**
         * 	0：成功
         * -400：请求错误
         * -404：无此项
         * 12002：评论区已关闭
         * 12009：评论主体的type不合法
         */
        val code: Int,
        val `data`: Data?,
        val message: String,
        val ttl: Int,
    )

    data class Data(
        /**
         * 页面信息
         */
        val page:Page,
        /**
         * 热评列表
         */
        val replies:List<ReplyRoot.Reply>?,
    )

    /**
     * @path root.data.page
     */
    data class Page(
        /**
         * 总计评论条数
         */
        val acount: Int,
        /**
         * 根评论条数
         */
        val count: Int,
        /**
         * 当前页码
         */
        val num: Int,
        /**
         * 每页项数
         */
        val size: Int,
    )
}