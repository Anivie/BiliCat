package ink.bluecloud.model.pojo.video.hot

import com.alibaba.fastjson2.annotation.JSONField

class VideoWeeklyListJsonRoot {
    data class Root(
        val code: Int,
        val `data`: Data?,
        val message: String,
        val ttl: Int
    )

    data class Data(
        /**
         * 这期视频的详细信息
         */
        val config: Config,
        /**
         * 视频列表
         */
        val list: List<Item>,
        /**
         * 提示
         */
        val reminder: String?
    )

    data class Config(
        val color: Int,
        val cover: String,
        val etime: Int,
        val hint: String,
        val id: Int,
        val label: String,
        val media_id: Int,
        val name: String,
        val number: Int,
        val share_subtitle: String,
        val share_title: String,
        val status: Int,
        val stime: Int,
        val subject: String,
        val type: String
    )

    data class Item(
        val aid: Long,
        val bvid: String,
        val cid: Long,
        /**
         * 转发：0
         * 原创：1
         */
        val copyright: Int,
        /**
         * 上传时间戳（s）
         */
        val ctime: Long,
        /**
         * 简介
         */
        val desc: String = "空",
        /**
         * 视频尺寸
         */
        val dimension: Dimension,
        /**
         * 视频长度 秒数
         */
        val duration: Long,
        val `dynamic`: String?,
        /**
         * 第一帧图像 URL
         */
        @JSONField(name = "first_frame")
        val firstFrameURL: String?,
        val is_ogv: Boolean,
        val mission_id: Long,
        val ogv_info: String?,
        /**
         * up主信息
         */
        val owner: Owner,
        /**
         * 封面
         */
        @JSONField(name = "pic")
        val cover: String,
        /**
         * 发布的城市.可以为null，是为了兼容以前视频
         */
        val pub_location: String?,
        /**
         * 视频发布日期（非视频上传日期）
         */
        val pubdate: Long,
        val rcmd_reason: String?,
        val rights: Rights?,
        val season_id: Int?,
        val season_type: Int?,
        @JSONField(name="short_link")
        val shortLink: String?,
        @JSONField(name="short_link_v2")
        val shortLinkV2: String?,
        /**
         * 视频状态，点赞等
         */
        val stat: Stat,
        val state: Int?,
        /**
         * 分区ID
         */
        val tid: Int,
        /**
         * 标题
         */
        val title: String,
        /**
         * 分区
         */
        val tname: String,
        val up_from_v2: Int,
        /**
         * 视频分p总数
         */
        val videos: Int
    )

    data class Dimension(
        val height: Int,
        val rotate: Int,
        val width: Int
    )

    data class Owner(
        val face: String,
        val mid: Long,
        val name: String
    )

    data class Rights(
        val arc_pay: Int,
        val autoplay: Int,
        val bp: Int,
        val download: Int,
        val elec: Int,
        val hd5: Int,
        val is_cooperation: Int,
        val movie: Int,
        val no_background: Int,
        val no_reprint: Int,
        val pay: Int,
        val pay_free_watch: Int,
        val ugc_pay: Int,
        val ugc_pay_preview: Int
    )

    data class Stat(
        val aid: Int,
        val coin: Int,
        val danmaku: Int,
        val dislike: Int,
        val favorite: Int,
        val his_rank: Int,
        val like: Int,
        val now_rank: Int,
        val reply: Int,
        val share: Int,
        val view: Int
    )
}