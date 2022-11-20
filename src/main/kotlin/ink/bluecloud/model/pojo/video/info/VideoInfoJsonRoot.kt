package ink.bluecloud.model.pojo.video.info

/**
 * 关于每个字段的意思，请猜测他，懒得现在写了
 */
class VideoInfoJsonRoot {

    data class Root(
        val code: Int,
        val `data`: Data?,
        val message: String,
        val ttl: Int,
    )

    data class Data(
        val aid: Long,
        val bvid: String,
        val cid: Long,
        val copyright: Int,
        val ctime: Long,//上传日期
        val desc_v2: List<DescV2>?,
        val dimension: Dimension,
        val duration: Long,
        val `dynamic`: String,
        val honor_reply: HonorReply,
        val is_chargeable_season: Boolean,
        val is_season_display: Boolean,
        val is_story: Boolean,
        val like_icon: String,
        val mission_id: Int,
        val no_cache: Boolean,
        val owner: Owner,
        val pages: List<Page>,
        val pic: String,
        val premiere: String?,
        val pubdate: Long,
//        val rights: String?,
        val stat: Stat,
        val state: Int,
        val subtitle: Subtitle?,
        val teenage_mode: Int,
        val tid: Int,
        val title: String,
        val tname: String,
        val user_garb: UserGarb,
        val videos: Int,
        val staff:ArrayList<StaffItem>?,
        var desc: String="空"
    )

    data class DescV2(
        val biz_id: Int,
        val raw_text: String,
        val type: Int,
    )

    data class Dimension(
        val height: Int,
        val rotate: Int,
        val width: Int,
    )

    class HonorReply

    data class Owner(
        val face: String,
        val mid: Long,
        val name: String,
    )

    data class Page(
        val cid: Long,
        val dimension: Dimension,
        val duration: Long,
        val from: String,
        val page: Int,
        val part: String,
        val vid: String,
        val weblink: String,
    )


    data class Stat(
        val aid: Long,
        val argue_msg: String,
        val coin: Long,
        val danmaku: Long,
        val dislike: Long,
        val evaluation: String,
        val favorite: Long,
        val his_rank: Int,
        val like: Long,
        val now_rank: Int,
        val reply: Long,
        val share: Long,
        val view: Long,
    )

    data class Subtitle(
        val allow_submit: Boolean,
        val list: List<CC>,
    )


    data class CC(
        val id: Long?,
        val lan: String?,
        val lan_doc: String?,
        val is_lock: Boolean?,
        val subtitle_url: String?,
        val type: Int?,
        val id_str: String?,
        val ai_type: Long?,
        val ai_status: Int?,
        val author_mid: Long?,
        val author: Author?,
    )

    data class Author(
        val birthday: Long,
        val face: String,
        val in_reg_audit: Int,
        val is_deleted: Int,
        val is_fake_account: Int,
        val is_senior_member: Int,
        val mid: Long,
        val name: String,
        val rank: Int,
        val sex: String,
        val sign: String,
    )

    data class UserGarb(
        val url_image_ani_cut: String,
    )

    data class StaffItem(
        val face: String,
        val follower: Long,
        val label_style: Int,
        val mid: Long,
        val name: String,
        val title: String,
    )

}