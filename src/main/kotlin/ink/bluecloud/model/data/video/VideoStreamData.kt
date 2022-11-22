package ink.bluecloud.model.data.video

import ink.bluecloud.network.http.HttpClient
import ink.bluecloud.network.http.HttpClientImpl
import ink.bluecloud.service.clientservice.video.stream.VideoStream
import ink.bluecloud.service.clientservice.video.stream.param.Qn
import ink.bluecloud.service.clientservice.video.stream.param.toQn
import okhttp3.Request
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.java.KoinJavaComponent.inject

/**
 * 视频流数据
 */
data class VideoStreamData(
    val video: StreamMap,
    val audio: StreamMap,
)

/**
 * 流数据
 */
data class StreamData(
    val qn: Int,
    val codec: Codec,
    val url: List<String>,
)

/**
 * 媒体数据映射表
 */
class StreamMap(list: List<StreamData>, isVideo: Boolean) : KoinComponent {
    private val videoQns: ArrayList<Qn> = ArrayList()
    private val audioQns: ArrayList<AudioQn> = ArrayList()
    private val list: ArrayList<StreamData> = ArrayList()
    private val qnMap: HashMap<Int, ArrayList<StreamData>> = HashMap()
    private var maxQN: Int = 0
    private val isVideo: Boolean

    init {
        this.isVideo = isVideo
        this.list.addAll(list)
        for (data in list) {
            val qnList = qnMap[data.qn] ?: ArrayList()
            qnList.add(data)
            qnMap[data.qn] = qnList
        }
    }

    /**
     * 获取清晰度最高的媒体数据
     */
    fun get(): StreamData {
        if (maxQN <= 0) maxQN = qnMap.keys.max()
        return get(maxQN)
    }

    /**
     * 获取指定清晰度的媒体数据
     * @param qn 清晰度
     */
    fun get(qn: Int): StreamData {
        return qnMap[qn]?.get(0) ?: throw NullPointerException("Not qn value found: $qn")
    }


    /**
     * 获取指定清晰度的音频媒体数据
     * @param qn 清晰度
     */
    fun getAudioStreamData(qn: AudioQn): StreamData {
        if (isVideo) throw IllegalStateException("This is not a audio media data map")
        return qnMap[qn.value]?.get(0) ?: throw NullPointerException("No audio QN value found: ${qn.value}")
    }

    /**
     * 获取关于指定清晰度的所有音频媒体数据
     * @param qn 清晰度
     */
    fun getAudioStreamDataAll(qn: AudioQn): ArrayList<StreamData> {
        if (isVideo) throw IllegalStateException("This is not a audio media data map")
        return qnMap[qn.value] ?: throw NullPointerException("Not audio QN value found: ${qn.value}")
    }

    /**
     * 获取指定清晰度的视频媒体数据
     * @param qn 清晰度
     */
    fun getVideoStreamData(qn: Qn): StreamData {
        if (!isVideo) throw IllegalStateException("This is not a video media data map")
        return qnMap[qn.value]?.get(0) ?: throw NullPointerException("No video QN value found: ${qn.value}")
    }


    /**
     * 获取指定清晰度与编码的视频媒体数据
     * @param qn 清晰度
     * @param codec 编码
     */
    fun getVideoStreamData(qn: Qn, codec: Codec): StreamData {
        if (!isVideo) throw IllegalStateException("This is not a video media data map")
        var data0: StreamData? = null
        for (data in getVideoStreamDataAll(qn)) if (codec.value == data.codec.value) data0 = data
        return data0 ?: throw NullPointerException("Not codec value found: ${codec.value}")
    }

    /**
     * 获取关于指定清晰度的所有视频媒体数据
     * @param qn 清晰度
     */
    fun getVideoStreamDataAll(qn: Qn): ArrayList<StreamData> {
        if (!isVideo) throw IllegalStateException("This is not a video media data map")
        return qnMap[qn.value] ?: throw NullPointerException("Not video QN value found: ${qn.value}")
    }


    /**
     * 获取所有媒体数据
     */
    fun getAll(): ArrayList<StreamData> {
        return this.list
    }

    /**
     * 获取视频所有清晰度
     */
    fun getVideoQnALL(): ArrayList<Qn> {
        if (!isVideo) throw IllegalStateException("This is not a video")
        if (videoQns.size > 0) return videoQns
        for (qn in this.qnMap.keys) {
            videoQns.add(qn.toQn())
        }
        return videoQns
    }

    /**
     * 获取音频所有清晰度
     */
    fun getAudioQnALL(): ArrayList<AudioQn> {
        if (isVideo) throw IllegalStateException("This is not a audio")
        if (audioQns.size > 0) return audioQns
        for (qn in this.qnMap.keys) {
            audioQns.add(qn.toAudioQn())
        }
        return audioQns
    }

    /**
     * 获取所有清晰度
     */
    fun getQnAllInt(): MutableSet<Int> {
        return this.qnMap.keys
    }


    /**
     * 是否是视频媒体数据
     */
    fun isVideoMedia(): Boolean {
        return this.isVideo
    }

    /**
     * 是否是视频媒体数据
     */
    fun isAudioMedia(): Boolean {
        return !isVideoMedia()
    }
}

/**
 * 从 StreamData 中获取存活的URL，如果没有就 throw IllegalStateException()
 * @Test
 */
suspend inline fun StreamData.keepURL(): String {
    val stream = VideoStream()
    for (item in url) {
        if (stream.keepUrl(item)) {
            return item
        }
    }
    throw NullPointerException()
}