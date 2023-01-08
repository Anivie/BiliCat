package ink.bluecloud.model.pojo.user.info.pendant

data class UserPendant(
    val expire: Int,
    /**
     * 头像框图片 url
     */
    val image: String?,
    /**
     * 头像框图片 url
     */
    val image_enhance: String?,
    val image_enhance_frame: String?,
    /**
     * 头像框名称
     */
    val name: String?,
    /**
     * 头像框 id
     */
    val pid: Int,
)