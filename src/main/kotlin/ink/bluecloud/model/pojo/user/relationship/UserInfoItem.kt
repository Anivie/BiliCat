package ink.bluecloud.model.pojo.user.relationship

import ink.bluecloud.model.pojo.user.info.OfficialVerify
import ink.bluecloud.model.pojo.user.info.vip.UserVip

data class UserInfoItem(
    /**
     * 关注属性
     * 0：未关注
     * 2：已关注
     * 6：已互粉
     */
    val attribute: Int,
    val contract_info: String?,
    /**
     * 用户头像url
     */
    val face: String,
    val face_nft: Int,
    val mid: Int,
    /**
     * 成为粉丝时间	时间戳
     * 互关后刷新
     */
    val mtime: Long,
    val nft_icon: String?,
    /**
     * 认证信息
     */
    val official_verify: OfficialVerify,
    val rec_reason: String?,
    /**
     * 用户签名
     */
    val sign: String?,
    val special: Int,
    val tag: String?,
    val track_id: String?,
    /**
     * 用户昵称
     */
    val uname: String,
    /**
     * 会员信息
     */
    val vip: UserVip,
)

