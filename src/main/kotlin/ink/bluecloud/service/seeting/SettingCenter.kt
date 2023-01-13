package ink.bluecloud.service.seeting

import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.model.networkapi.api.data.HttpApi
import ink.bluecloud.utils.logger
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.reflect.KType

abstract class SettingCenter {
    val configPath = mapOf(
        CookieJson::class.java to "Cookie.proto".toPath(),
        HttpApi::class.java to "HttpAPI.proto".toPath(),
    )

    protected val logger = logger()

    abstract fun <T : Any> loadSetting(clazz: Class<T>): T?
    abstract fun <T: Any> checkSettingIsNull(clazz: Class<T>): Boolean
    abstract fun <T: Any> saveSetting(t: T, clazz: KType)

    @Suppress("NOTHING_TO_INLINE")
    private inline fun String.toPath(): Path {
        return Paths.get("config",this)
    }
}