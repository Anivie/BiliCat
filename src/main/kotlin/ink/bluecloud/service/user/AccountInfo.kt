package ink.bluecloud.service.user

import ink.bluecloud.model.data.account.AccountCard
import ink.bluecloud.model.pojo.user.account.AccountInfoJsonRoot
import ink.bluecloud.service.APIResources
import ink.bluecloud.utils.getForStream
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.onIO
import ink.bluecloud.utils.toObjJson
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.koin.core.annotation.Factory

/**
 * 获取账号基本信息
 */
@Factory
class AccountInfo : APIResources() {
    /**
     * 获取这个账号自己的信息（需要Cookie）
     */
    suspend fun getAccountInfo() = onIO {
        getJsonPOJO().data.run {
            AccountCard(
                name = uname!!,
                mid = mid!!,
                coin = money!!,
                vip = vipStatus == 1,
                head = ioScope.async(start = CoroutineStart.LAZY) {
                    face?.run {
                        httpClient.getForStream(toHttpUrl())
                    }?: throw IllegalArgumentException("No head picture found!")
                },

                level = level_info!!.current_level,
                levelCurrentExp = level_info.current_exp,
                levelCurrentMin = level_info.current_min,
                levelNextExp = level_info.next_exp,

                pid = pendant!!.pid,
                expire = pendant.expire,
                pendantName = pendant.name,
                image = ioScope.async(start = CoroutineStart.LAZY) {
                    pendant.image?.run {
                        httpClient.getForStream(toHttpUrl())
                    }
                },
                imageEnhanceFrame = ioScope.async(start = CoroutineStart.LAZY) {
                    pendant.image_enhance_frame?.run {
                        httpClient.getForStream(toHttpUrl())
                    }
                },
                imageEnhance = ioScope.async(start = CoroutineStart.LAZY) {
                    pendant.image_enhance?.run {
                        httpClient.getForStream(toHttpUrl())
                    }
                },

                officialDesc = official!!.desc!!,
                officialRole = official.role,
                officialTitle = official.title!!,
                officialType = official.type
            )
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