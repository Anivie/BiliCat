package ink.bluecloud.service.clientservice.video.stream

import ink.bluecloud.model.data.video.StreamData
import ink.bluecloud.model.data.video.StreamMap
import ink.bluecloud.model.data.video.VideoStreamData
import ink.bluecloud.model.data.video.toCodec
import ink.bluecloud.model.pojo.video.stream.VideoStreamJsonRoot
import ink.bluecloud.service.clientservice.APIResources
import ink.bluecloud.service.clientservice.video.stream.param.VideoStreamParamBuilder
import ink.bluecloud.service.clientservice.video.stream.param.toQn
import ink.bluecloud.utils.getForHead
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.toObjJson
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.koin.core.annotation.Factory

/**
 * 获取视频的URL
 * @TestAPI: 等待测试API稳定后移除此项
 * @Building: 构建API中
 */
@Factory
class VideoStream : APIResources() {
    /**
     * 获取视频播放流
     * @param bvid 视频 BV 号
     * @param cid 视频的 cid，用来定位视频当前第几 page
     * @param builder 视频播放信息流参数构造器，用来兼容 durl 模式，对于 dash 格式请默认即可
     * @TestAPI: 等待测试API稳定后移除此项
     */
    suspend fun getVideoStream(
        bvid: String,
        cid: Long,
        builder: VideoStreamParamBuilder = VideoStreamParamBuilder(),
    ): VideoStreamData {
        val data = getJsonPOJO(bvid, cid, builder).data ?: throw NullPointerException("video stream response data is null")
        val dash = data.dash ?: throw NullPointerException("dash is null")

        //构建视频URL列表
        val videos = StreamMap(dash.video.map { it.getStreamData() }, true)

        //构建音频URL列表
        val audios = StreamMap(dash.audio.map { it.getStreamData() }, false)

        //整合
        return VideoStreamData(video = videos, audio = audios, data.accept_quality.map { it.toQn() },data.accept_description)
    }

    /**
     * 获取媒体数据
     */
    private fun VideoStreamJsonRoot.Media.getStreamData(): StreamData {
        val urls = buildList<String> list@{
            baseUrl?.run { this@list += this }
            base_url?.run { this@list += this }
            backupUrl?.run { this@list += this }
            backup_url?.run { this@list += this }
        }

        //返回视频stream封装
        return StreamData(id, codecs.toCodec(), urls)
    }


    suspend fun keepUrl(
        url: String,
    ): Boolean {
        return httpClient.getForHead(url.toHttpUrl())
    }

    /**
     * 获取视频的URL JSON数据
     * @param bvid 视频的bv号
     * @param cid 视频的cid，如果没有请通过工具类 IDConvert 获取
     * @param builder 视频参数构造器
     */
    private suspend fun getJsonPOJO(
        bvid: String,
        cid: Long,
        builder: VideoStreamParamBuilder = VideoStreamParamBuilder(),
    ): VideoStreamJsonRoot.Root {
        val api = api(API.getVideoStreamURL, getParams = builder.build()){
            it["bvid"] = bvid
            it["cid"] = cid.toString()
        }
        return httpClient.getForString(api.url).toObjJson(VideoStreamJsonRoot.Root::class.java)
    }
}