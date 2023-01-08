package ink.bluecloud.model.pojo.comment.info

data class CommentUpper(
    /**
     * UP 主 mid
     */
    val mid: Int,
    /**
     * 置顶条目
     */
//        val top: Top?,
    val top: String?,
    /**
     * 投票评论？
     */
    val vote: String?,
)

/**
 * @see Reply
 */
data class Top(
    /**
     * 当前用户操作状态,需要登录(Cookie 或 APP) 否则恒为 0
     * 0：无
     * 1：已点赞
     * 2：已点踩
     */
    val action: Int,
    /**
     * 右上角卡片标签信息
     */
    val card_label: List<ReplyRoot.CardLabel>?,
    /**
     * 评论信息
     */
    val content: ReplyRoot.Content?,
    /**
     * 二级评论条数
     */
    val count: Int,
    /**
     * 回复评论条数
     */
    val rcount: Int,
    /**
     * 评论发送时间	时间戳
     */
    val ctime: Int,
    /**
     * 回复对方 rpid
     * 若为一级评论则为 0
     * 若为二级评论则为该评论 rpid
     * 大于二级评论为上一级评论 rpid
     */
    val dialog: Int,
    /**
     * 是否具有粉丝标签
     * 0：无
     * 1：有
     */
    val fansgrade: Int,
    /**
     * 评论楼层号
     */
    val floor: Int?,
    /**
     * 折叠信息
     */
    val folder: ReplyRoot.Folder?,
    /**
     * 评论获赞数
     */
    val like: Int,
    /**
     * 评论发送者信息
     */
    val member: ReplyRoot.Member?,
    /**
     * 发送者 mid
     */
    val mid: Long,
    /**
     * 评论区对象 id
     */
    val oid: Int,
    /**
     * 回复父评论 rpid
     * 若为一级评论则为 0
     * 若为二级评论则为根评论 rpid
     * 大于二级评论为上一级评 论 rpid
     */
    val parent: Int,
    /**
     * 评论回复条目预览
     * 仅嵌套一层否则为 null
     */
    val replies: List<ReplyRoot.Reply>?,
    /**
     * 评论提示文案信息
     */
    val reply_control: ReplyRoot.ReplyControl?,
    /**
     * 根评论 rpid
     * 若为一级评论则为 0
     * 大于一级评论则为根评论 id
     */
    val root: Int,
    val rpid: Long,
//        val state: Int,
    /**
     * 评论区类型代码
     */
    val type: Int,
    /**
     * 评论 UP 主操作信息
     */
    val up_action: ReplyRoot.UpAction?,
)