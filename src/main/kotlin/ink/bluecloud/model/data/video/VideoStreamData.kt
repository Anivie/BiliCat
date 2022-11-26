@file:Suppress("unused")

package ink.bluecloud.model.data.video

import ink.bluecloud.service.clientservice.video.stream.VideoStream
import ink.bluecloud.service.clientservice.video.stream.param.Qn
import ink.bluecloud.service.clientservice.video.stream.param.toQn
import org.koin.core.component.KoinComponent

/**
 * 视频流数据
 */
data class VideoStreamData(
    val video: StreamMap,
    val audio: StreamMap,
    /**
     * 视频支持的视频清晰度列表
     */
    val acceptQuality:List<Qn>,
    val acceptQualityDescribe:List<String>
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
        notAudio()
        return qnMap[qn.value]?.get(0) ?: throw NullPointerException("No audio QN value found: ${qn.value}")
    }

    /**
     * 获取关于指定清晰度的所有音频媒体数据
     * @param qn 清晰度
     */
    fun getAudioStreamDataAll(qn: AudioQn): ArrayList<StreamData> {
        notAudio()
        return qnMap[qn.value] ?: throw NullPointerException("Not audio QN value found: ${qn.value}")
    }

    /**
     * 获取指定清晰度的视频媒体数据
     * @param qn 清晰度
     */
    fun getVideoStreamData(qn: Qn): StreamData {
        notVideo()
        return qnMap[qn.value]?.get(0) ?: throw NullPointerException("No video QN value found: ${qn.value}")
    }


    /**
     * 获取指定清晰度与编码的视频媒体数据
     * @param qn 清晰度
     * @param codec 编码
     */
    fun getVideoStreamData(qn: Qn, codec: Codec): StreamData {
        notVideo()
        return getVideoStreamDataAll(qn).filter {
            it.codec.value == codec.value
        }.run {
            if (isEmpty()) throw IllegalArgumentException("Not codec value found: ${codec.value}")
            first()
        }
    }

    /**
     * 获取关于指定清晰度的所有视频媒体数据
     * @param qn 清晰度
     */
    fun getVideoStreamDataAll(qn: Qn): List<StreamData> {
        notVideo()
        return qnMap[qn.value] ?: throw NullPointerException("Not video QN value found: ${qn.value}")
    }


    /**
     * 获取所有媒体数据
     */
    fun getAll(): ArrayList<StreamData> = list

    /**
     * 获取视频所有清晰度
     */
    fun getVideoQnALL(): List<Qn> {
        return videoQns.ifEmpty {//第一次使用，构建缓存
            videoQns.apply {
                qnMap.keys.forEach {
                    this += it.toQn()
                }
            }.sortedByDescending {//对清晰度排序
                it.index
            }.apply {//保存进缓存
                videoQns.forEachIndexed { index, qn ->
                    if (qn != this[index]) videoQns[index] = this[index]
                }
            }
        }
    }


    /**
     * 获取音频所有清晰度
     */
    fun getAudioQnALL(): ArrayList<AudioQn> {
        notAudio()
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

    private fun notVideo() {
        if (!isVideo) throw IllegalStateException("This is not a video")
    }

    private fun notAudio() {
        if (isVideo) throw IllegalStateException("This is not a video")
    }
}

/**
 * 从 StreamData 中获取存活的URL，如果没有就 throw IllegalStateException()
 * @Test
 */
suspend inline fun StreamData.tryAvailableURL(stream: VideoStream): String {
    url.forEach {
        if (stream.keepUrl(it)) return it
    }
    throw NullPointerException()
}