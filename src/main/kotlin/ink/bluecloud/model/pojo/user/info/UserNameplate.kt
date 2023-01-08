package ink.bluecloud.model.pojo.user.info

data class UserNameplate(
    /**
     * 勋章条件
     */
    val condition: String,
    /**
     * 挂件图片 url 正常
     */
    val image: String,
    /**
     * 勋章图片 url 小
     */
    val image_small: String,
    /**
     * 勋章等级
     */
    val level: String,
    /**
     * 勋章名称
     */
    val name: String,
    /**
     * 勋章 id
     */
    val nid: Int,
)