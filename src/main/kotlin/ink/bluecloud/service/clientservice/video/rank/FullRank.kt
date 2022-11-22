package ink.bluecloud.service.clientservice.video.rank

import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.model.pojo.video.hot.RankListJsonRoot
import ink.bluecloud.service.ClientService
import ink.bluecloud.utils.getForString
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
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 全站排行榜
 */
@Factory
class FullRank : ClientService() {

    /**
     * 获取某期周榜视频列表 以 Video数据类型
     */
    suspend fun getVideos() = io {
        flow {
            getJsonPojo().data?.list?.forEach {
                HomePagePushCard(
                    title = it.title,
                    author = it.owner.name,
                    mid = it.owner.mid,
                    id = it.bvid,
                    cid = it.cid,
                    time = Date(it.ctime * 1000),
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

/*
    private val cover: suspend CoroutineScope.(RankListJsonRoot.Item) -> Deferred<InputStream> = {
        async(start = CoroutineStart.LAZY) {
            println(it)
            suspendCoroutine { coroutine ->
                httpClient.getFor(it.pic.toHttpUrl()) {
                    coroutine.resume(body.byteStream())
                    logger.info("获取热榜视频封面成功，返回值${code}.")
                }
            }
        }
    }
*/

    private val cover: suspend CoroutineScope.(RankListJsonRoot.Item) -> InputStream = {
        suspendCoroutine { coroutine ->
            httpClient.getFor(it.pic.toHttpUrl()) {
                coroutine.resume(body.byteStream())
                logger.info("获取热榜视频封面成功，返回值${code}.")
            }
        }
    }

    private suspend fun getJsonPojo():RankListJsonRoot.Root {
        return httpClient.getForString(netWorkResourcesProvider.api.getFullRank)
            .toObjJson(RankListJsonRoot.Root::class.java)
    }
}