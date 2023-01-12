package ink.bluecloud.service.clientservice.video.hot

import ink.bluecloud.exceptions.PojoException
import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.model.pojo.video.hot.VideoHotListJsonRoot
import ink.bluecloud.utils.toObjJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flow
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.koin.core.annotation.Factory
import java.io.InputStream
import java.time.Duration
import java.util.*
import kotlin.collections.set
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 获取热门视频列表 API
 * @API https://api.bilibili.com/x/web-interface/popular?ps=1&pn=1
 * @TestAPI: 等待测试API稳定后移除此项
 */
@Factory
class HotVideoList: FrontVideo() {

    /**
     * 获取热门视频列表
     */
    override suspend fun getVideos() = io {
        flow {
            getJsonPOJO(1, 50).data?.list?.forEach {
                HomePagePushCard(
                    id = it.bvid,
                    cid = it.cid,
                    title = it.title,
                    duration = Duration.ofSeconds(it.duration),
                    mid = it.owner.mid,
                    author = it.owner.name,
                    playVolume = it.stat.view,
                    barrageVolume = it.stat.danmaku,
                    time = Date(it.pubdate * 1000),
                    cover = ioScope.async(start = CoroutineStart.LAZY) {
                        cover(it)
                    }
                ).run {
                    emit(this)
                }
            }
        }
    }

    private val cover: suspend CoroutineScope.(VideoHotListJsonRoot.Item) -> InputStream = {
        suspendCoroutine { coroutine ->
            httpClient.getFor(it.pic.toHttpUrl()) {
                coroutine.resume(body.byteStream())
                logger.info("获取热榜视频封面成功，返回值${code}.")
            }
        }
    }

    /**
     * 获取热门视频列表 JSON POJO
     * @param pn 页数
     * @param ps 获取视频数量总数
     * @exception PojoException 将会抛出一个POJO解析异常，此时通常是服务器返回的JSON 字段发生了变动导致的不兼容
     */
    private suspend fun getJsonPOJO(pn: Int = 1, ps: Int = 50): VideoHotListJsonRoot.Root {
        return getPage(pn, ps).toObjJson(VideoHotListJsonRoot.Root::class.java)
    }

    /**
     * 获取热门视频列表原始数据
     * @param pn 页数
     * @param ps 获取视频数量总数
     */
    private suspend fun getPage(pn: Int = 1, ps: Int = 50) = suspendCoroutine { coroutine ->
        val api = api( API.getHotVideoList) {
            it["pn"] = pn.toString()
            it["ps"] = ps.toString()
        }

        httpClient.getFor(api.url) {
            coroutine.resume(body.string())
            logger.info("获取榜单视频列表成功，返回值${code}.")
        }
    }
}