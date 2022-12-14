package ink.bluecloud.service.clientservice.comments.info.enums

/**
 * 评论区类型ID
 */
enum class CommentType(val value:Int)  {
    /**
     * 视频的AVID: 视频的 AVID （纯数字）
     */
    AV_ID(1),
    /**
     * 话题
     * oid:话题 id
     */
    TOPIC_OF_CONVERSATION(2),
    /**
     * 活动:活动 id
     */
    ACTIVITY(4),
    /**
     * 小视频:	小视频 id
     */
    ACTIVITY_VIDEO(5),
    /**
     * 视频稿件:稿件_avid
     */
    VIDEO_SUBMISSIONS(1),
    /**
     * 话题:话题_id
     */
    TOPIC(2),
    /**
     * 小视频:小视频_id
     */
    A_SMALL_VIDEO(5),
    /**
     * 小黑屋封禁信息:封禁公示_id
     */
    THE_LITTLE_BLACK_HOUSE_INFORMATION(6),
    /**
     * 公告信息:公告_id
     */
    ANNOUNCEMENT_OF_THE_INFORMATION(7),
    /**
     * 直播活动:直播间_id
     */
    LIVE_EVENTS(8),
    /**
     * 活动稿件:(?)
     */
    ACTIVITIES_OF_MANUSCRIPT(9),
    /**
     * 直播公告:(?)
     */
    BROADCAST_ANNOUNCEMENT(10),
    /**
     * 相簿:相簿_id
     */
    PHOTO_ALBUM(11),
    /**
     * 专栏:专栏_cvid
     */
    COLUMN(12),
    /**
     * 票务:(?)
     */
    TICKET(13),
    /**
     * 音频:音频_auid
     */
    AUDIO(14),
    /**
     * 风纪委会:众裁项目_id
     */
    THE_WIND_DISCIPLINARUM_COMMITTEE(15),
    /**
     * 点评:(?)
     */
    REVIEW(16),
    /**
     * 动态:动态_id
     */
    DYNAMIC(17),
    /**
     * 播单:(?)
     */
    SOWING_SINGLE(18),
    /**
     * 音乐播单:(?)
     */
    MUSIC_PLAY_SINGLE(19),
    /**
     * 漫画:(?)
     */
    COMIC(20),
    /**
     * 漫画:(?)
     */
    COMIC_2(21),
    /**
     * 漫画:漫画_mcid
     */
    COMIC_MCID(22),
    /**
     * 课程:课程_epid
     */
    COURSE_EPID(33),


}