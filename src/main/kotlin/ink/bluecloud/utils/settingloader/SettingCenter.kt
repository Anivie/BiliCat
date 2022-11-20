package ink.bluecloud.utils.settingloader

import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.utils.logger
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.reflect.KType

abstract class SettingCenter {
    val configPath = mapOf<Class<out Any>, Path>(
        CookieJson::class.java to "config${File.separator}Cookie.proto".toPath(),
    )

    protected val logger = logger()

    abstract fun readAllSetting()
    abstract fun <T : Any> readSettingOnly(clazz: Class<T>): T?
    abstract fun <T> getSetting(clazz: Class<T>): T?
    abstract fun <T: Any> saveSetting(t: T, clazz: KType)

    @Suppress("NOTHING_TO_INLINE")
    private inline fun String.toPath(): Path {
        return Paths.get(this)
    }
}