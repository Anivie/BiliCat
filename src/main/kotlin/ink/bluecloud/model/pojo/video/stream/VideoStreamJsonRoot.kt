package ink.bluecloud.model.pojo.video.stream

import ink.bluecloud.model.data.video.Video

/**
 * 获取视频的URL
 * @TestAPI: 等待测试API稳定后移除此项
 */
class VideoStreamJsonRoot {
    data class Root(
        val code: Int,
        val `data`: Data?,
        val message: String,
        val ttl: Int,
    )

    data class Data(
//        val accept_description: List<String>,
//        val accept_format: String,
        val accept_quality: List<Int>,

        val durl : ArrayList<DurlItem>?,
        val dash: Dash?,

        val format: String,
//        val from: String,
//        val high_format: Any,
//        val last_play_cid: Int,
//        val last_play_time: Int,
//        val message: String,
//        val quality: Int,
//        val result: String,
//        val seek_param: String,
//        val seek_type: String,
//        val support_formats: List<SupportFormat>,
//        val timelength: Int,
//        val video_codecid: Int,
    )


    data class DurlItem(
        val ahead: String,
        val backup_url: List<String>,
        val length: Int,
        val order: Int,
        val size: Int,
        val url: String,
        val vhead: String,
    )

    data class Dash(
        val audio: List<Video.AudioStreamJson>,
        val dolby: String?,
        val duration: String?,
        val flac: String?,
        val minBufferTime: Double,
        val min_buffer_time: Double,
        val video: List<Video.VideoStreamJson>,
    )

    data class SupportFormat(
        val codecs: List<String>,
        val display_desc: String,
        val format: String,
        val new_description: String,
        val quality: Int,
        val superscript: String,
    )

    data class SegmentBase(
        val Initialization: String,
        val indexRange: String,
    )

    data class SegmentBaseX(
        val index_range: String,
        val initialization: String,
    )
}