package ink.bluecloud.model.pojo.comment.info

import ink.bluecloud.model.pojo.user.info.LevelInfo
import ink.bluecloud.model.pojo.user.info.OfficialVerify
import ink.bluecloud.model.pojo.user.info.UserNameplate
import ink.bluecloud.model.pojo.user.info.pendant.UserPendant
import ink.bluecloud.model.pojo.user.info.vip.UserVip


/**
 * 评论条目对象
 */
class ReplyRoot {
    /**
     * 评论条目对象
     */
    data class Reply(
        /**
         * 当前用户操作状态,需要登录(Cookie 或 APP) 否则恒为 0
         * 0：无
         * 1：已点赞
         * 2：已点踩
         */
        val action: Int,
        /**
         * 右上角卡片标签信息
         */
        val card_label: List<CardLabel>?,
        /**
         * 评论信息
         */
        val content: Content,
        /**
         * 二级评论条数
         */
        val count: Int,
        /**
         * 回复评论条数
         */
        val rcount: Int,
        /**
         * 评论发送时间	时间戳
         */
        val ctime: Long,
        /**
         * 回复对方 rpid
         * 若为一级评论则为 0
         * 若为二级评论则为该评论 rpid
         * 大于二级评论为上一级评论 rpid
         */
        val dialog: Int,
        val dynamic_id_str: String,
        /**
         * 是否具有粉丝标签
         * 0：无
         * 1：有
         */
        val fansgrade: Int,
        /**
         * 评论楼层号
         */
        val floor: Int?,
        /**
         * 折叠信息
         */
        val folder: Folder,
        val invisible: Boolean,
        /**
         * 评论获赞数
         */
        val like: Int,
        /**
         * 评论发送者信息
         */
        val member: Member,
        /**
         * 发送者 mid
         */
        val mid: Long,
        /**
         * 评论区对象 id
         */
        val oid: Int,
        /**
         * 回复父评论 rpid
         * 若为一级评论则为 0
         * 若为二级评论则为根评论 rpid
         * 大于二级评论为上一级评 论 rpid
         */
        val parent: Int,
        val parent_str: String,
        /**
         * 评论回复条目预览
         * 仅嵌套一层否则为 null
         */
        val replies: List<Reply>?,
        /**
         * 评论提示文案信息
         */
        val reply_control: ReplyControl,
        /**
         * 根评论 rpid
         * 若为一级评论则为 0
         * 大于一级评论则为根评论 id
         */
        val root: Int,
        val root_str: String,
        val rpid: Long,
        val rpid_str: String,
        /**
         * 评论区类型代码
         */
        val type: Int,
        /**
         * 评论 UP 主操作信息
         */
        val up_action: UpAction,
    )

    /**
     * 右上角卡片标签信息
     */
    data class CardLabel(
        /**
         * 背景图片 url
         */
        val background: String,
        /**
         * 背景图片高度
         */
        val background_height: Int,
        /**
         * 背景图片宽度
         */
        val background_width: Int,
        /**
         * 跳转链接
         */
        val jump_url: String,
        /**
         * 日间标签颜色
         */
        val label_color_day: String,
        /**
         * 夜间标签颜色
         */
        val label_color_night: String,
        /**
         * 	评论 rpid
         */
        val rpid: Long,
        /**
         * 日间文本颜色	十六进制颜色值，下同
         */
        val text_color_day: String,
        /**
         * 夜间文本颜色
         */
        val text_color_night: String,
        /**
         * 标签文本:已知有妙评
         */
        val text_content: String,
    )

    /**
     * 评论者信息
     */
    data class Member(
        /**
         * 发送者头像 url
         */
        val avatar: String,
        val face_nft_new: Int,
        /**
         * 是否关注该用户	需要登录(Cookie或APP)否则恒为0
         * 0：未关注
         * 1：已关注
         */
        val following: Int?,
        /**
         * 发送者粉丝标签
         */
//        val fans_detail: FansDetail?,
        val fans_detail: String?,

        /**
         * 是否被该用户关注	需要登录(Cookie或APP)否则恒为0
         * 0：未关注
         * 1：已关注
         */
        val is_followed: Boolean?,
        val is_senior_member: Int,
        /**
         * 发送者等级
         */
        val level_info: LevelInfo,
        /**
         * 发送者 mid
         */
        val mid: Long,
        /**
         * 发送者勋章信息
         */
        val nameplate: UserNameplate,
        val nft_interaction: String?,
        /**
         * 发送者认证信息
         */
        val official_verify: OfficialVerify,
        /**
         * 发送者头像框信息
         */
        val pendant: UserPendant?,
        /**
         * 发送者性别	男 女 保密
         */
        val sex: String,
        /**
         * 发送者签名
         */
        val sign: String,
        /**
         * 发送者昵称
         */
        val uname: String,
        /**
         * 发送者评论条目装扮信息
         */
//        val user_sailing: UserSailing?,
        val user_sailing: String?,
        /**
         * 发送者会员信息
         */
        val vip: UserVip,
    )

    /**
     * 评论信息
     */
    data class Content(
        /**
         * 需要渲染的表情转义	评论内容无表情则无此项
         */
//        val emote: Emote,
        val emote: HashMap<String, EmoteItem>?,
        /**
         * 需要高亮的超链转义
         */
        val jump_url: String,
        /**
         * 收起最大行数
         */
        val max_line: Int,
        /**
         * at 到的用户信息
         */
        val members: List<Member>,
        /**
         * 评论内容
         */
        val message: String,
        /**
         * 评论发送平台设备
         */
        val device: String?,
        /**
         * 评论发送端
         * 1：web端
         * 2：安卓客户端
         * 3：ios 客户端
         * 4：wp 客户端
         */
        val plat: Int?,
    )

    /**
     * 折叠信息
     */
    data class Folder(
        /**
         * 是否有被折叠的二级评论
         */
        val has_folded: Boolean,
        /**
         * 评论是否被折叠
         */
        val is_folded: Boolean,
        /**
         * 相关规则页面 url
         */
        val rule: String,
    )

    /**
     * 评论提示文案
     */
    data class ReplyControl(
        /**
         * 回复提示	共 xx 条回复
         */
        val sub_reply_entry_text: String?,
        /**
         * 回复提示	相关回复共有 xx 条
         */
        val sub_reply_title_text: String?,
        /**
         * 时间提示	xx 天/小时 前发布
         */
        val time_desc: String?,
    )

    /**
     * UP 操作
     */
    data class UpAction(
        /**
         * 是否UP主觉得很赞
         */
        val like: Boolean,
        /**
         * 是否被UP主回复
         */
        val reply: Boolean,
    )

    data class EmoteItem(
//        val attr: Int,
        /**
         * 表情 id
         */
        val id: Int,
        /**
         * 表情名称
         */
        val jump_title: String,
        /**
         * 属性信息
         */
        val meta: Meta,
        /**
         * 表情创建时间
         */
        val mtime: Int,
        /**
         * 表情包 id
         */
        val package_id: Int,
        val state: Int,
        /**
         * 表情转义符
         */
        val text: String,
        /**
         * 表情类型
         * 1：免费
         * 2：会员专属
         * 3：购买所得
         * 4：颜文字
         */
        val type: Int,
        /**
         * 表情图片 url
         */
        val url: String,
    )


    data class Meta(
        /**
         * 表情尺寸信息
         * 1：小
         * 2：大
         */
        val size: Int,
        /**
         * 简写名
         */
        val alias: String?,
    )

    data class UserSailing(
        /**
         * 评论卡片装扮
         */
        val cardbg: String?,
        val cardbg_with_focus: String?,
        /**
         * 头像框信息
         */
        val pendant: UserPendant?,
    )

    data class FansDetail(
        /**
         * 用户 mid
         */
        val uid:Long,
        /**
         * 粉丝标签 id
         */
        val medal_id:Long,
        /**
         * 粉丝标签名
         */
        val medal_name:String?,
        /**
         * 当前标签等级
         */
        val level:Int,
    )
}