package ink.bluecloud.service.video.hot

import com.alibaba.fastjson2.to
import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.model.pojo.video.hot.VideoWeeklyHistoryListJsonRoot
import ink.bluecloud.model.pojo.video.hot.VideoWeeklyListJsonRoot
import ink.bluecloud.utils.getForStream
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.onIO
import ink.bluecloud.utils.toObjJson
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flow
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.koin.core.annotation.Factory
import java.time.Duration
import java.util.*
import kotlin.collections.set
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 视频周榜
 * @API:https://api.bilibili.com/x/web-interface/popular/series/one?number={page}
 */
@Factory
class VideoWeeklyList : FrontVideo() {
    /**
     * 获取某期周榜视频列表 以 Video数据类型
     */
     override suspend fun getVideos() = onIO {
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
                        logger.info("获取周榜视频封面成功!")
                        httpClient.getForStream(it.cover.toHttpUrl())
                    }
                ).run {
                    emit(this)
                }
            }
        }
    }

    /**
     * 获取某期周榜视频列表 以 json格式
     */
    private suspend fun getJsonPojoToVideoWeeklyList(page: Int, ):VideoWeeklyListJsonRoot.Root {
        return getPageOfVideoWeeklyList(page).toObjJson(VideoWeeklyListJsonRoot.Root::class.java)
    }

    /**
     * 获取周榜视频
     * @param page 期数，表述为b站创建周榜第n期收录的视频
     */
    private suspend fun getPageOfVideoWeeklyList(page: Int) = suspendCoroutine { c ->
        val api = api(API.getVideoWeeklyList){
            it["number"] = page.toString()
        }

        httpClient.getFor(api.url) {
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