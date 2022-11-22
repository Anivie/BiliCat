package ink.bluecloud.service.clientservice.video.hot

import com.alibaba.fastjson2.to
import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.model.networkapi.api.NetWorkResourcesProvider
import ink.bluecloud.model.pojo.video.hot.VideoWeeklyHistoryListJsonRoot
import ink.bluecloud.model.pojo.video.hot.VideoWeeklyListJsonRoot
import ink.bluecloud.service.ClientService
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.param
import ink.bluecloud.utils.toObjJson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.koin.core.annotation.Factory
import org.koin.core.component.get
import java.io.InputStream
import java.time.Duration
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 视频周榜
 * @API:https://api.bilibili.com/x/web-interface/popular/series/one?number={page}
 */
@Factory
class VideoWeeklyList : ClientService() {
    /**
     * 获取某期周榜视频列表 以 Video数据类型
     */
     suspend fun getVideos() = io {
        val (_, data, _, _) = getJsonPojoToVideoWeeklyList(getNewWeeklyNumber().number)
        flow {
            data?.list?.forEach {
                HomePagePushCard(
                    title = it.title,
                    author = it.owner.name,
                    mid = it.owner.mid,
                    id = it.bvid,
                    cid = it.cid,
                    time = Date(it.pubdate * 1000),
                    duration = Duration.ofSeconds(it.duration),
                    playVolume = it.stat.view,
                    barrageVolume = it.stat.danmaku,
                    cover = ioScope.async(start = CoroutineStart.LAZY) {
                        cover(it)
                    }
                ).run {
                    emit(this)
                }
            }
        }
    }

    private val cover: suspend CoroutineScope.(VideoWeeklyListJsonRoot.Item) -> InputStream = {
        suspendCoroutine { coroutine ->
            httpClient.getFor(it.cover.toHttpUrl()) {
                coroutine.resume(body.byteStream())
                logger.info("获取热榜视频封面成功，返回值${code}.")
            }
        }
    }


/*
    val cover: suspend CoroutineScope.(VideoWeeklyListJsonRoot.Item) -> Deferred<InputStream> = { item ->
        async(start = CoroutineStart.LAZY) {
            suspendCoroutine { continuation ->
                httpClient.getFor(item.cover.toHttpUrl()) {
                    continuation.resume(body.byteStream())
                    logger.info("获取周榜视频封面成功，返回值${code}.")
                }
            }
        }
    }
*/

    /**
     * 获取某期周榜视频列表 以 json格式
     */
    private suspend fun getJsonPojoToVideoWeeklyList(page: Int, ):VideoWeeklyListJsonRoot.Root {
        return getPageOfVideoWeeklyList(page).toObjJson(VideoWeeklyListJsonRoot.Root::class.java)
    }

    /**
     * 获取周榜视频
     * @param page 期数，表述为b站创建周榜第n期收录的视频
     * @param handle 回调函数，用来处理获取到的周榜视频
     */
    private suspend fun getPageOfVideoWeeklyList(page: Int) = suspendCoroutine { c ->
        val param = get<NetWorkResourcesProvider>().api.getVideoWeeklyList.param {
            it["number"] = page.toString()
        }
        httpClient.getFor(param) {
            c.resume(body.string())
        }
    }

    /**
     * 获取最新的期数
     */
    private suspend fun getNewWeeklyNumber(): VideoWeeklyHistoryListJsonRoot.Item {
        return httpClient.getForString(netWorkResourcesProvider.api.getVideoWeeklyHistoryList)
            .to<VideoWeeklyHistoryListJsonRoot.Root>().data?.list?.maxBy { it.number }!!
    }
}