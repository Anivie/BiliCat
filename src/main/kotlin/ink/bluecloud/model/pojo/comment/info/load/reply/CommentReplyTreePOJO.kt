package ink.bluecloud.model.pojo.comment.info.load.reply

import ink.bluecloud.model.pojo.comment.info.CommentConfig
import ink.bluecloud.model.pojo.comment.info.CommentControl
import ink.bluecloud.model.pojo.comment.info.CommentUpper
import ink.bluecloud.model.pojo.comment.info.ReplyRoot

class CommentReplyTreePOJO {
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
         * 页楼层信息
         */
        val cursor:Cursor,
        /**
         * 对话楼层信息
         */
        val dialog:Dialog,
        /**
         * 评论区显示控制
         */
        val config: CommentConfig,
        /**
         * 评论区输入属性
         */
        val control: CommentControl?,
        /**
         * 评论对话树列表
         */
        val replies: List<ReplyRoot.Reply>?,
        /**
         * up信息
         */
        val upper: CommentUpper,
        )

    data class Cursor(
        /**
         * 本页最低对话楼层
         */
        val min_floor:Int,
        /**
         * 本页最高对话楼层
         */
        val max_floor:Int,
        /**
         * 本页项数
         */
        val size:Int,
    )

    data class Dialog(
        /**
         * 二级评论最低对话楼层
         */
        val min_floor:Int,
        /**
         * 二级评论最高对话楼层
         */
        val max_floor:Int,
    )
}