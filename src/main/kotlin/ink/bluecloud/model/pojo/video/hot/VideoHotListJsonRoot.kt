package ink.bluecloud.model.pojo.video.hot

class VideoHotListJsonRoot {
    data class Root(
    val code: Int,
    val `data`: Data?,
    val message: String,
    val ttl: Int
)

data class Data(
    val list: List<Item>,
    val no_more: Boolean
)

data class Item(
    val aid: Long,
    val bvid: String,
    val cid: Long,
    val copyright: Int,
    val ctime: Int,
    val desc: String,
    val dimension: Dimension,
    val duration: Long,
    val `dynamic`: String?,
    val first_frame: String?,
    val is_ogv: String?,
    val mission_id: Long,
    val ogv_info: String?,
    val owner: Owner,
    val pic: String,
    val pub_location: String?,
    val pubdate: Long,
    val rcmd_reason: RcmdReason?,
    val redirect_url: String?,
    val rights: Rights,
    val season_id: Int,
    val season_type: Int,
    val short_link: String,
    val short_link_v2: String,
    val stat: Stat,
    val state: Int,
    val tid: Int,
    val title: String,
    val tname: String,
    val up_from_v2: Int,
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

data class RcmdReason(
    val content: String?,
    val corner_mark: Int?
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
    val aid: Long,
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