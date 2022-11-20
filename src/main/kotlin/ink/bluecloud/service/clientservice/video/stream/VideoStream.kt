package ink.bluecloud.service.clientservice.video.stream

import ink.bluecloud.model.data.video.StreamMap
import ink.bluecloud.model.data.video.VideoStreamData
import ink.bluecloud.model.pojo.video.stream.VideoStreamJsonRoot
import ink.bluecloud.service.ClientService
import ink.bluecloud.service.clientservice.video.stream.param.VideoStreamParamBuilder
import ink.bluecloud.utils.param
import ink.bluecloud.utils.toObjJson
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.koin.core.annotation.Factory

/**
 * 获取视频的URL
 * @TestAPI: 等待测试API稳定后移除此项
 * @Building: 构建API中
 */
@Factory
class VideoStream : ClientService() {
    fun getVideoStream(
        bvid: String,
        cid: Long,
        builder: VideoStreamParamBuilder = VideoStreamParamBuilder(),
        handle: (VideoStreamData) -> Unit,
    ) {
        getJsonPOJO(bvid, cid, builder) { json ->
            val dash = json.data?.dash ?: throw NullPointerException("dash is null")
            val videos = StreamMap()
            val audios = StreamMap()
            dash.video.forEach { video ->
                val id = video.id
                val codecs = video.codecs
                val baseUrl = video.baseUrl
                val backupUrl = video.base_url
                val backupUrlList = video.backupUrl
                val backupUrlList2 = video.backup_url

                val urls = ArrayList<String>()
                if (baseUrl != null) urls.add(baseUrl)
                if (backupUrl != null) urls.add(backupUrl)
                if (backupUrlList != null) urls.addAll(backupUrlList)
                if (backupUrlList2 != null) urls.addAll(backupUrlList2)
                //添加URL
                videos.put(id, codecs, urls)
            }
            dash.audio.forEach { audio ->
                val id = audio.id
                val codecs = audio.codecs
                val baseUrl = audio.baseUrl
                val backupUrl = audio.base_url
                val backupUrlList = audio.backupUrl
                val backupUrlList2 = audio.backup_url

                val urls = ArrayList<String>()
                urls.add(baseUrl)
                if (backupUrl != null) urls.add(backupUrl)
                if (backupUrlList != null) urls.addAll(backupUrlList)
                if (backupUrlList2 != null) urls.addAll(backupUrlList2)
                //添加URL
                audios.put(id, codecs, urls)
            }
            handle(VideoStreamData(video = videos, audio = audios))
        }
    }

    fun getLength(
        url: String,
        handle: (length: Long) -> Unit,
    ) {
        httpClient.getFor(url.toHttpUrl()) {
            val lenStr = headers["Content-Length"]
            handle(lenStr?.toLong() ?: -1)
        }
    }


    /**
     * 获取视频的URL JSON数据
     * @param bvid 视频的bv号
     * @param cid 视频的cid，如果没有请通过工具类 IDConvert 获取
     * @param builder 视频参数构造器
     * @param handle 回调函数，可内置JSON数据处理逻辑
     */
    fun getJsonPOJO(
        bvid: String,
        cid: Long,
        builder: VideoStreamParamBuilder = VideoStreamParamBuilder(),
        handle: (VideoStreamJsonRoot.Root) -> Unit,
    ) {
        getPage(bvid, cid, builder) {
            val json = it.toObjJson(VideoStreamJsonRoot.Root::class.java)
            handle(json)
        }
    }

    /**
     * 获取视频的URL 原始数据
     * @param bvid 视频的bv号
     * @param cid 视频的cid，如果没有请通过工具类 IDConvert 获取
     * @param builder 视频参数构造器,默认为Dash 760P视频
     * @param handle 回调函数，可内置原始数据处理逻辑
     */
    private fun getPage(
        bvid: String,
        cid: Long,
        builder: VideoStreamParamBuilder = VideoStreamParamBuilder(),
        handle: (String) -> Unit,
    ) = newIO {
        val param = netWorkResourcesProvider.api.getVideoStreamURL.param(builder.build()) {
            it.put("bvid", bvid)
            it.put("cid", cid.toString())
        }

        httpClient.getFor(param) {
            handle(body.string())
        }
    }
}