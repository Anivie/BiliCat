package ink.bluecloud.service.video.stream.param

enum class Fnval(val type:Int,val value:Int) {
    /**
     * flv格式
     * 仅H.264编码
     * 部分老视频存在分段现象
     * 与mp4格式及dash格式互斥
     */
    FLV(0,0),
    /**
     * mp4格式
     * 仅H.264编码
     * 不存在视频分段
     * 与flv格式及dash格式互斥
     */
    MP4(1,1),
    /**
     * dash格式
     * H.264编码或H.265编码
     * 部分老视频的清晰度上限低于flv格式
     * 与mp4格式及flv格式互斥
     */
    Dash(16,16),
    /**
     * 杜比世界（HDR） 视频
     * 必须为dash格式
     * 需要qn=125,如果QN为125，程序自动添加该条
     */
    Dash_HDR(64,16),

    /**
     * 杜比视界
     */
    Dash_DOLBY(512,16),

    /**
     * 杜比音频
     */
    Dash_DOLBY_AUDIO(256,16),

    /**
     * 8K 分辨率
     */
    Dash_8K(1024,16),

    /**
     * av1 编码
     */
    Dash_av1(2048,16),

    /**
     * 4K 分辨率
     * 该值与fourk字段协同作用
     * 需要qn=120
     */
    P4K(128,0),
}