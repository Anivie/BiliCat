package ink.bluecloud.model.pojo.user.relationship

/**
 * 通用用户关系 pojo 类
 */
class UserRelationshipUniversallyPOJO {
    data class Root(
        val code: Int,
        val `data`: Data?,
        val message: String,
        val ttl: Int,
    )

    data class Data(
        /**
         * 明细列表
         */
        val list: List<UserInfoItem>?,
        val re_version: Int,
        /**
         * 粉丝总数
         */
        val total: Int,
    )
}