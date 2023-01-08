package ink.bluecloud.model.pojo.comment.info.load.reply

import ink.bluecloud.model.pojo.comment.info.CommentConfig
import ink.bluecloud.model.pojo.comment.info.CommentControl
import ink.bluecloud.model.pojo.comment.info.CommentUpper
import ink.bluecloud.model.pojo.comment.info.ReplyRoot

class CommentReplyPOJO {
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
         * 评论区显示控制
         */
        val config: CommentConfig,
        /**
         * 评论区输入属性
         */
        val control: CommentControl?,
        /**
         * 页信息
         */
        val page: Page,
        /**
         * 评论列表，最大数为20
         */
        val replies: List<ReplyRoot.Reply>?,
        /**
         * up信息
         */
        val upper: CommentUpper,

        val root:String
        )

    /**
     * @path root.data.page
     */
    data class Page(
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