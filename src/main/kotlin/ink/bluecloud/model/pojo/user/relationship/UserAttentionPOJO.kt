package ink.bluecloud.model.pojo.user.relationship

import ink.bluecloud.model.pojo.user.info.vip.UserVip

class UserAttentionPOJO {
    data class Root(
        val code: Int,
        val `data`: Data?,
        val message: String,
        val ttl: Int,
    )

    data class Data(
        val list: List<Item>,
        val re_version: Int,
        /**
         * 关注总数
         */
        val total: Int,
    )

    data class Item(
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
        val face_nft: Int?,
        val mid: Int,
        /**
         * 成为粉丝时间
         */
        val mtime: Int,
//        val nft_icon: String,
        /**
         * 认证信息
         */
        val official_verify: OfficialVerify,
//        val rec_reason: String,
        /**
         * 签名
         */
        val sign: String,
        /**
         *  特别关注标志
         */
        val special: Boolean,
        /**
         * 分组id
         */
        val tag: List<String>?,
        val track_id: String?,
        val uname: String,
        val vip: UserVip,
    )

    data class OfficialVerify(
        val desc: String,
        val type: Int,
    )
}