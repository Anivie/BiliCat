package ink.bluecloud.model.pojo.comment.info

data class CommentConfig(
    /**
     * 是否显示管理置顶
     */
    val showadmin: Boolean?,
    /**
     * 是否显示楼层号
     */
    val showfloor: Boolean?,
    /**
     * 是否显示话题
     */
    val showtopic: Boolean?,
    /**
     * 是否显示“UP 觉得很赞”标志
     */
    val show_up_flag: Boolean,
    /**
     * 是否只读评论区
     */
    val read_only: Boolean,
    /**
     * 是否显示删除记录
     */
    val show_del_log: Boolean?,
//        val showentry: Int,
)