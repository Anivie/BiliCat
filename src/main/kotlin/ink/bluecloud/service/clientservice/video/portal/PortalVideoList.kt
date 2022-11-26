package ink.bluecloud.service.clientservice.portal

import ink.bluecloud.exceptions.PojoException
import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.model.pojo.video.portal.PortalVideoJsonRoot
import ink.bluecloud.service.ClientService
import ink.bluecloud.utils.param
import ink.bluecloud.utils.toObjJson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.koin.core.annotation.Factory
import java.io.InputStream
import java.time.Duration
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 获取首页视频推荐 API
 * @API https://api.bilibili.com/x/web-interface/index/top/feed/rcmd?ps=1
 * @TestAPI: 等待测试API稳定后移除此项
 */
@Factory
class PortalVideoList : ClientService() {
    /**
     * 获取首页视频推荐
     * @param num 首页推荐的视频个数。此参数存在最大值，当超过最大值服务器会自动返回最大个数的视频数
     * @param handle 回调函数，用来处理当前已经被解析出来的视频单元
     */
    suspend fun getVideoList(num: Int = 11) = io {
        flow {
            getJsonPOJO(num).data?.item?.forEach {
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
                        println(this)
                        cover(it.pic)
                    }
                ).run {
                    emit(this)
                }
            }
        }
    }

    private val cover: suspend CoroutineScope.(String) -> InputStream = {
        suspendCoroutine { coroutine ->
            httpClient.getFor(it.toHttpUrl()) {
                coroutine.resume(body.byteStream())
                logger.info("获取热榜视频封面成功，返回值${code}.")
            }
        }
    }

/*
    val cover: suspend CoroutineScope.(String) -> Deferred<InputStream> = { item ->
        println(item)
        async(start = CoroutineStart.LAZY) {
            println(this)
            suspendCoroutine { continuation ->
                httpClient.getFor(item.toHttpUrl()) {
                    continuation.resume(body.byteStream())
                }
            }
        }
    }
*/

    /**
     * 获取首页视频推荐 POJO类
     * @param num 首页推荐的视频个数。此参数存在最大值，当超过最大值服务器会自动返回最大个数的视频数
     * @param handle 回调函数，用来处理获取到的POJO类
     * @exception PojoException 将会抛出一个POJO解析异常，此时通常是服务器返回的JSON 字段发生了变动导致的不兼容
     */
     private suspend fun getJsonPOJO(num: Int = 11):PortalVideoJsonRoot.Root {
        return getPage(num).toObjJson(PortalVideoJsonRoot.Root::class.java)
    }

    /**
     * 获取首页视频推荐原始数据
     * @param num 首页推荐的视频个数。此参数存在最大值，当超过最大值服务器会自动返回最大个数的视频数
     * @param handle 回调函数，用来处理获取到的原始数据
     */
    private suspend fun getPage(num: Int = 11):String = suspendCoroutine { coroutine ->
        val param = netWorkResourcesProvider.api.getVideoStreamURL.param {
            it["ps"] = num.toString()
        }
        logger.debug("API Get PortalVideo -> $param")
        httpClient.getFor(param) {
            coroutine.resume(body.string())
        }
    }
}
