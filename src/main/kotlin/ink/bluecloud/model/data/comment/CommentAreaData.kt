package ink.bluecloud.model.data.comment

import ink.bluecloud.model.pojo.comment.info.ReplyRoot

/**
 * @BUILD
 */
data class CommentAreaData (
    val videoId:Long,
    val commentId:Long,
    val replies:List<ReplyRoot.Reply>
)