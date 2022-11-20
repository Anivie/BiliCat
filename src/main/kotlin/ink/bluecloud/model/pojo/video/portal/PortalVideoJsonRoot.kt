package ink.bluecloud.model.pojo.video.portal

class PortalVideoJsonRoot{
    data class Root(
        val code: Int,
        val `data`: Data?,
        val message: String?,
        val ttl: Int
    )

    data class Data(
        val business_card: String?,
        val floor_info: String?,
        val item: List<Item>?,
        val preload_expose_pct: Double?,
        val preload_floor_expose_pct: Double?,
    )

    data class Item(
        val av_feature: String?,
        val business_info: String?,
        val bvid: String,
        val cid: Long,
        val duration: Long,
//        val goto: String?, 与Java关键字冲突
        val id: Long,
        val is_followed: Int,
        val is_stock: Int,
        val ogv_info: String?,
        val owner: Owner,
        val pic: String,
        val pos: Int,
        val pubdate: Long,
        val room_info: String?,
        val show_info: Int,
        val stat: Stat,
        val title: String,
        val track_id: String?,
        val uri: String
    )

    data class Owner(
        val face: String?,
        val mid: Long,
        val name: String
    )
    data class Stat(
        val danmaku: Int,
        val like: Int,
        val view: Int
    )
}