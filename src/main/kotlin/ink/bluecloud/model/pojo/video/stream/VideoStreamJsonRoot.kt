package ink.bluecloud.model.pojo.video.stream


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
        val accept_quality: List<Int>,

        val durl : ArrayList<DurlItem>?,
        val dash: Dash?,

        val format: String,
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
//        val audio: List<Video.AudioStreamJson>,
        val audio: List<Media>,
        val dolby: String?,
        val duration: String?,
        val flac: String?,
        val minBufferTime: Double,
        val min_buffer_time: Double,
//        val video: List<Video.VideoStreamJson>,
        val video: List<Media>,
    )

    data class SegmentBase(
        val Initialization: String,
        val indexRange: String,
    )

    data class SegmentBaseX(
        val index_range: String,
        val initialization: String,
    )


    data class Media(
        /**
         * 下载链接
         */
        val backupUrl: ArrayList<String>?,

        /**
         * 下载链接
         */
        val backup_url: ArrayList<String>?,

        /**
         * 视频最低带宽
         */
        val bandwidth: Int,

        /**
         * 下载链接
         */
        val baseUrl: String?,

        /**
         * 下载链接
         */
        val base_url: String?,

        /**
         * 编码类型
         */
        val codecid: Long,

        /**
         * 编码类型
         */
        val codecs: String,
        val frameRate: String,
        val frame_rate: String,
        val height: Int,

        /**
         * 清晰度等
         */
        val id: Int,

        /**
         * 文件类型
         */
        val mimeType: String,

        /**
         * 文件类型
         */
        val mime_type: String,

        /**
         * 文件类型
         */
        var type: String?,
        val sar: String,
        val startWithSap: Int,
        val start_with_sap: Int,
        val width: Int,
    )
}