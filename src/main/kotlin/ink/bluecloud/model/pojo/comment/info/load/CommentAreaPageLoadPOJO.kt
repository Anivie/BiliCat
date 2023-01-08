package ink.bluecloud.model.pojo.comment.info.load

import ink.bluecloud.model.pojo.comment.info.CommentConfig
import ink.bluecloud.model.pojo.comment.info.CommentControl
import ink.bluecloud.model.pojo.comment.info.CommentUpper
import ink.bluecloud.model.pojo.comment.info.ReplyRoot


class CommentAreaPageLoadPOJO {
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
         * 折叠相关信息
         */
        val folder: ReplyRoot.Folder?,
        /**
         * 评论区类型id
         * @see ink.bluecloud.service.clientservice.comments.info.enums.CommentType
         */
        val mode: Int,
        /**
         * 评论区支持的类型id
         */
        val support_mode: List<Int>?,
        /**
         * 页信息
         */
        val page: Page,
        /**
         * 评论列表
         */
        val replies: List<ReplyRoot.Reply>?,
        /**
         * 热评列表
         */
        val hots: List<ReplyRoot.Reply>?,
        /**
         * 置顶评论
         */
        val upper: CommentUpper,
        /**
         * 评论区公告信息
         */
        val notice: String?,
        /**
         * 疑似投票评论
         */
        val vote: Int,
        /**
         * 置顶评论
         */
        val top_replies: List<ReplyRoot.Reply>?,
        /**
         * 抽奖评论
         */
        val lottery_card: String?,
        /**
         * 	广告
         */
        val cm: String?,
        /**
         * 广告控制
         */
        val cm_info: String?,

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