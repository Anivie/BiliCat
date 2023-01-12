package ink.bluecloud.service.clientservice.video.rank

import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.model.pojo.video.hot.RankListJsonRoot
import ink.bluecloud.service.clientservice.video.hot.FrontVideo
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

/**
 * 全站排行榜
 */
@Factory
class FullRank : FrontVideo() {

    /**
     * 获取某期总站热门视频列表 以 Video数据类型
     */
    override suspend fun getVideos() = onIO {
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
                        logger.info("获取总站热门视频封面成功!")
                        httpClient.getForStream(it.pic.toHttpUrl())
                    }
                ).run {
                    emit(this)
                }
            }
        }
    }

    private suspend fun getJsonPojo(): RankListJsonRoot.Root {
        val api = api(API.getFullRank)
        return httpClient.getForString(api.url)
            .toObjJson(RankListJsonRoot.Root::class.java)
    }
}