package ink.bluecloud.model.pojo.user.account

import ink.bluecloud.model.pojo.user.info.LevelInfo
import ink.bluecloud.model.pojo.user.info.OfficialVerify
import ink.bluecloud.model.pojo.user.info.pendant.UserPendant

class AccountInfoJsonRoot {
    data class Root(
        val code: Int,
        val `data`: Data,
        val message: String,
        val ttl: Int,
    )

    /**
     * 阉割版
     */
    data class Data(
        val face: String?,
        /**
         * 是否为硬核会员
         * 0：否
         * 1：是
         */
        val is_senior_member: Int?,
        /**
         * 等级
         */
        val level_info: LevelInfo?,
        val mid: Long?,
        /**
         * 节操
         */
        val moral:Int?,
        /**
         * 硬币数
         */
        val money: Int?,
        /**
         * 认证信息
         */
        val official: OfficialVerify?,
        /**
         * 头像框
         */
        val pendant: UserPendant?,
        /**
         * 用户名称
         */
        val uname: String?,
        /**
         * vip 状态
         * 0：无   1：有
         */
        val vipStatus: Int?,
        /**
         * 大会员类型	0：无
         *         1：月度大会员
         *         2：年度及以上大会员
         */
        val vipType: Int?,
        /**
         * 支付类型	0：未支付（常见于官方账号）
         * 1：已支付（以正常渠道获取的大会员均为此值）
         */
        val vip_pay_type: Int?,
    )
}