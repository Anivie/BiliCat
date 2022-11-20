package ink.bluecloud.model.pojo.video.hot

class RankListJsonRoot {
    data class Root(
        val code: Int,
        val `data`: Data?,
        val message: String,
        val ttl: Int,
    )

    data class Data(
        val list: List<Item>,
        val note: String,
    )

    data class Item(
        val aid: Int,
        val bvid: String,
        val cid: Long,
        val copyright: Int,
        val ctime: Long,
        val desc: String,
        val dimension: Dimension,
        val duration: Long,
//        val `dynamic`: String?,
//        val first_frame: String?,
//        val mission_id: Int,
//        val others: List<Other>,
        val owner: OwnerX,
        val pic: String,
        val pub_location: String?,
        val pubdate: Long,
//        val rights: RightsX,
//        val score: Int,
//        val season_id: Int,
//        val short_link: String,
//        val short_link_v2: String,
        val stat: StatX,
//        val state: Int,
        val tid: Int,
        val title: String,
        val tname: String,
//        val up_from_v2: Int,
//        val videos: Int,
    )

    data class Dimension(
        val height: Int,
        val rotate: Int,
        val width: Int,
    )

    data class Other(
        val aid: Long,
        val attribute: Int,
        val attribute_v2: Int,
        val bvid: String,
        val cid: Long,
        val copyright: Int,
        val ctime: Int,
        val desc: String,
        val dimension: Dimension,
        val duration: Int,
        val `dynamic`: String,
        val first_frame: String,
        val owner: Owner,
        val pic: String,
        val pub_location: String,
        val pubdate: Int,
//        val rights: Rights,
        val score: Int,
        val short_link: String,
        val short_link_v2: String,
//        val stat: Stat,
        val state: Int,
        val tid: Int,
        val title: String,
        val tname: String,
        val up_from_v2: Int,
        val videos: Int,
    )

    data class OwnerX(
        val face: String,
        val mid: Long,
        val name: String,
    )

    data class RightsX(
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
        val ugc_pay_preview: Int,
    )

    data class StatX(
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
        val view: Int,
    )

    data class Owner(
        val face: String,
        val mid: Long,
        val name: String,
    )
}