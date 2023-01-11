package ink.bluecloud.service.clientservice.user

import ink.bluecloud.model.data.account.AccountCard
import ink.bluecloud.model.pojo.user.account.AccountInfoJsonRoot
import ink.bluecloud.service.ClientService
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.toObjJson
import org.koin.core.annotation.Factory
import java.io.InputStream
import java.net.URL

/**
 * 获取账号基本信息
 */
@Factory
class AccountInfo : ClientService() {
    /**
     * 获取这个账号自己的信息（需要Cookie）
     */
    suspend fun getAccountInfo() = io {
        return@io getJsonPOJO().data.run {
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

    private fun getInputStream(str: String): InputStream? {
        if (str.trim().isNotEmpty()) {
            return URL(str).openStream()
        }
        return null
    }

    /**
     * 获取这个账号自己的信息（需要Cookie）  JSON-POJO
     */
    private suspend fun getJsonPOJO():AccountInfoJsonRoot.Root {
        logger.info("API Get AccountInfo -> ${netWorkResourcesProvider.api.getAccountInfo}")
       return httpClient.getForString(netWorkResourcesProvider.api.getAccountInfo)
           .toObjJson(AccountInfoJsonRoot.Root::class.java)
    }
}