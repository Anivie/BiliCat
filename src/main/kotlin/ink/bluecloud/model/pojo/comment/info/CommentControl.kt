package ink.bluecloud.model.pojo.comment.info

data class CommentControl(
    /**
     * 答题页面安卓 url
     */
    val answer_guide_android_url: String,
    /**
     * 答题页面图标 url
     */
    val answer_guide_icon_url: String,
    /**
     * 答题页面 ios url
     */
    val answer_guide_ios_url: String,
    /**
     * 答题页面链接文字
     */
    val answer_guide_text: String,
    /**
     * 空评论区文字
     */
    val bg_text: String,
    /**
     * 评论框文字
     */
    val child_input_text: String,
    val disable_jump_emote: Boolean,
    val giveup_input_text: String,
    val input_disable: Boolean,
    /**
     * 评论框文字
     */
    val root_input_text: String,
    val screenshot_icon_state: Int,
    val show_text: String,
    val show_type: Int,
    val upload_picture_icon_state: Int,
    /**
     * 评论是否筛选后可见
     */
    val web_selection: Boolean,
)