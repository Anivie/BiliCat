package ink.bluecloud.model.pojo.comment.info.load

import ink.bluecloud.model.pojo.comment.info.CommentConfig
import ink.bluecloud.model.pojo.comment.info.CommentControl
import ink.bluecloud.model.pojo.comment.info.CommentUpper
import ink.bluecloud.model.pojo.comment.info.ReplyRoot


class CommentAreaLazyLoadPOJO {
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
         * 游标信息
         */
        val cursor: Cursor?,
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

    data class Cursor(
        /**
         * 全部评论条数
         */
        val all_count: Int,
        /**
         * 是否为第一页
         */
        val is_begin: Boolean,
        /**
         * 上页页码
         */
        val prev: Int,
        /**
         * 下页页码
         */
        val next: Int,
        /**
         * 是否为最后页
         */
        val is_end: Int,
        /**
         * 排序方式
         */
        val mode: Int,
        val show_type: Int?,
        /**
         * 支持的排序方式
         */
        val support_mode: List<String>,
        /**
         * 评论区类型名
         */
        val name: String,
    )
}