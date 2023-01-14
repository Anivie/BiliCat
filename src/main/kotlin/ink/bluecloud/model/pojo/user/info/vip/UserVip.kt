package ink.bluecloud.model.pojo.user.info.vip

import ink.bluecloud.model.pojo.user.relationship.UserAttentionPOJO

data class UserVip(
//        val accessStatus: Int,
//        val avatar_subscript: Int,
//        val dueRemark: String,
        /**
         * 会员铭牌样式
         */
        val label: UserVipLabel?,
        /**
         * 昵称颜色
         */
        val nickname_color: String?,
        /**
         * 会员样式 id
         */
        val themeType: Int,
        /**
         * 大会员到期时间	毫秒 时间戳
         */
        val vipDueDate: Long,
        /**
         * 大会员状态
         * 0：无
         * 1：有
         */
        val vipStatus: Int,
//        val vipStatusWarn: String,
        /**
         * 大会员类型
         * 0：无
         * 1：月会员
         * 2：年以上会员
         */
        val vipType: Int,
    )