package ink.bluecloud.service.clientservice.user

import ink.bluecloud.model.data.account.AccountCard
import ink.bluecloud.model.pojo.user.account.AccountInfoJsonRoot
import ink.bluecloud.service.clientservice.APIResources
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.io
import ink.bluecloud.utils.toObjJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.koin.core.annotation.Factory
import java.io.InputStream
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 获取账号基本信息
 */
@Factory
class AccountInfo : APIResources() {
    /**
     * 获取这个账号自己的信息（需要Cookie）
     */
    suspend fun getAccountInfo() = io {
        getJsonPOJO().data.run {
            AccountCard(
                name = uname!!,
                mid = mid!!,
                coin = money!!,
                vip = vipStatus == 1,
                head = URL(face).openStream(),

                level = level_info!!.current_level,
                levelCurrentExp = level_info.current_exp,
                levelCurrentMin = level_info.current_min,
                levelNextExp = level_info.next_exp,

                pid = pendant!!.pid,
                expire = pendant.expire,
                pendantName = pendant.name,
                image = getInputStream(pendant.image!!),
                imageEnhanceFrame = getInputStream(pendant.image_enhance_frame!!),
                imageEnhance = getInputStream(pendant.image_enhance!!),

                officialDesc = official!!.desc!!,
                officialRole = official.role,
                officialTitle = official.title!!,
                officialType = official.type
            )
        }
    }

    private fun CoroutineScope.getInputStream(str: String): Deferred<InputStream> = async {
        suspendCoroutine { coroutine ->
            httpClient.getFor(str.toHttpUrl()) {
                coroutine.resume(body.byteStream())
                logger.info("获取热榜视频封面成功，返回值${code}.")
            }
        }
    }

    /**
     * 获取这个账号自己的信息（需要Cookie）  JSON-POJO
     */
    private suspend fun getJsonPOJO(): AccountInfoJsonRoot.Root {
        val api = api(API.getAccountInfo)
        return httpClient.getForString(api.url)
            .toObjJson(AccountInfoJsonRoot.Root::class.java)
    }
}