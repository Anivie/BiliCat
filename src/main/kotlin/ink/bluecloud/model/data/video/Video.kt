package ink.bluecloud.model.data.video


data class Video(
    /**
     * 视频的AVID可与BVID互相转换
     * 视频的唯一标识符，可以标记一个多p视频，或一个多片段视频
     */
    val avid: Long,
    /**
     * 视频的BVID可与AVID互相转换
     * 视频的唯一标识符，可以标记一个多p视频，或一个多片段视频
     */
    val bvid: String,
    /**
     * 视频当前分p的标识，通常为视频第一个分P的标识。
     * 即便是有多个分P，或者只有一个分p的视频
     */
    val cid: Long,
    /**
     * 视频的标题
     */
    val title: String,
    /**
     * 稿件总时长(所有分P) 单位为秒
     */
    val duration: Long,
    /**
     * 视频封面的URL
     */
    val cover: String,
    /**
     * 稿件时间记录
     */
    val time: Time,
    /**
     * 视频的URL，通常为：https://www.bilibili.com/video/{BVID}
     */
    val url: String = "https://www.bilibili.com/video/${bvid}",
    /**
     * 视频的CID列表
     * 视频有多少分p 就有多少CID，每一个分p 就对应一个唯一的CID。
     * 此字段在部分情况下都为null，因此想要获取不为null 的CID 列表请 使用方法: toCids()
     */
    val cids: List<Long>,
    /**
     * 稿件简介
     */
    val describe: Describe? = null,
    /**
     * 稿件类型，原创、转载
     */
    val type: Type? = null,
    /**
     * 视频分P
     */
    val page: PageEntrance? = null,

    /**
     * CC Subtitle
     */
    val CCSubtitle: Subtitle? = null,

    /**
     * upID
     */
    val authorMid: Long? = null,
    /**
     * 主要up的名称
     */
    val authorName: String? = null,
    /**
     * 主要up的头像
     */
    val authorFace: String? = null,
    /**
     * 合作up列表《名称，MID》
     */
    val authors: List<StaffItem>? = null,
    /**
     * 视频状态：点赞、投币等
     */
    val stat: Stat? = null,
    /**
     * 视频尺寸
     */
    val dimension: Dimension? = null,
    /**
     * 视频分区
     * TODO("未实现,等待分区列表与接口完成")
     */
    val partition: String? = null,
    /**
     * 视频上传时的城市
     */
    val location:String? = null,
) {
    /**
     * 合作UP
     */
    data class StaffItem(
        /**
         * 头像URL
         */
        val face: String,
        /**
         * 粉丝数
         */
        val follower: Long,
        /**
         * MID
         */
        val mid: Long,
        /**
         * NAME
         */
        val name: String,
        /**
         * 担任的职位
         */
        val position: String,
    )

    /**
     * 稿件时间
     */
    data class Time(
        /**
         * 稿件公布日期 时间戳
         */
        val publishDate: Long,
        /**
         * 稿件上传日期 时间戳
         */
        var uploadDate: Long,
    )

    /**
     * 简介
     */
    data class Describe(
        /**
         * 简介
         */
        var describe: String,
        /**
         * 简介第二版
         */
        var describeV2: List<DescV2>? = null,
    )

    /**
     * 第二版简介
     */
    data class DescV2(
        val biz_id: Int,
        val raw_text: String,
        val type: Int,
    )

    /**
     * 稿件类型：转载、原创
     */
    data class Type(
        /**
         * 类型：
         * 1原创
         * 2转载
         */
        var type: Int,
    ) {
        /**
         * 是否是原创
         */
        fun isOriginal() = type == 1

        /**
         * 类型名称
         */
        fun typeStr(): String {
            if (type == 1) return "原创"
            else return "转载"
        }
    }

    /**
     * 视频分p管理
     */
    data class PageEntrance(
        /**
         * 视频page列表（无序）
         */
        val pages: List<Page>,
        /**
         * 视频有多少个page
         */
        val length: Int = pages.size,
    )

    /**
     * 分p
     */
    data class Page(
        /**
         * 当前page 的cid
         */
        val cid: Long,
        /**
         * 当前分P分辨率	,部分较老视频无分辨率值
         */
        var dimension: Dimension,
        /**
         * 当前分P持续时间:单位为秒
         */
        val duration: Long,
        /**
         * 视频来源:<br>
         * vupload：普通上传（B站）<br>
         * hunan：芒果TV<br>
         * qq：腾讯<br>
         */
        var from: String,
        /**
         * 当前page所在page列表中位置
         */
        val page: Int,
        /**
         * 当前分P标题
         */
        val title: String,
        /**
         * 站外视频vid	仅站外视频有效
         */
        var vid: String?,
        var weblink: String,
    )

    /**
     * 分辨率
     */
    data class Dimension(
        var height: Int,
        var rotate: Int,
        var width: Int,
    )

    /**
     * 视频的CC弹幕
     */
    data class Subtitle(
        /**
         * 是否允许提交字幕
         */
        var allow_submit: Boolean,
        /**
         * 字幕列表
         */
        val list: List<CC>,
    )

    data class CC(
        /**
         * 字幕id
         */
        var id: Long?,
        /**
         * 字幕语言
         */
        var lan: String?,
        /**
         * 字幕语言名称
         */
        var lanDoc: String?,
        /**
         * 是否锁定
         */
        var is_lock: Boolean?,
        /**
         * json格式字幕文件url
         */
        var url: String?,
        var type: Int?,
        /**
         * AI 类型
         */
        var aiType: Long?,
        /**
         * AI 状态
         */
        var aiStatus: Int?,
        /**
         * 字幕上传者mid
         */
        var authorMid: Long?,
    )

    data class Stat(
        /**
         * 播放数
         */
        var view: Long,
        /**
         * 获赞数
         */
        var like: Long,
        /**
         * 警告/争议提示信息
         */
        var argue_msg: String? = null,
        /**
         * 投币数
         */
        var coin: Long? = null,
        /**
         * 弹幕数
         */
        var danmaku: Long? = null,
        /**
         * 点踩数
         */
        var dislike: Long? = null,
        /**
         * 视频评分
         */
        var evaruation: String? = null,
        /**
         * 收藏数
         */
        var favorite: Long? = null,
        /**
         * 历史最高排行: 大于1000不显示
         */
        var hisRank: Int? = null,


        /**
         * 当前排名：大于1000不显示
         */
        var nowRank: Int? = null,
        /**
         * 评论数
         */
        var reply: Long? = null,
        /**
         * 分享数
         */
        var share: Long? = null,
    )
}


