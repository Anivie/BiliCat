@file:Suppress("OPT_IN_USAGE", "UNCHECKED_CAST")
@file:OptIn(InternalSerializationApi::class)

package ink.bluecloud.service.seeting

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.serializer
import org.koin.core.annotation.Single
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import kotlin.io.path.exists
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.jvm.jvmErasure

@Single
class SettingCenterImpl : SettingCenter() {
    private val settingCache = HashMap<String, Any>()

    /**
    * Load setting.
     * If it exists in cache, it will return it from cache(singleton),
    *  if not, load from config path.
    * */
    override fun <T : Any> loadSetting(clazz: Class<T>): T? {
        return (settingCache[clazz.simpleName]as? T)?:
        configPath[clazz]?.run {
            if (exists()) {
                ProtoBuf.decodeFromByteArray(clazz.kotlin.serializer(), Files.readAllBytes(this)).apply {
                    settingCache[clazz.simpleName] = this
                }
            } else null
        }
    }

    override fun <T : Any> checkSettingIsNull(clazz: Class<T>) = loadSetting(clazz) == null

    override fun <T: Any> saveSetting(t: T, clazz: KType) {
        settingCache[clazz.javaClass.simpleName]?.run {
            settingCache[clazz.javaClass.simpleName] = t
        }

        configPath[clazz.jvmErasure.java]?.run {
            logger.info("Saving cookies in $this")
            Files.write(this,ProtoBuf.encodeToByteArray(serializer(clazz)as KSerializer<T>,t),StandardOpenOption.CREATE)
        }?: throw IllegalArgumentException("Un suport setting:${clazz.jvmErasure.java}")
    }
}

inline fun <reified T : Any> SettingCenter.loadSetting(): T? {
    return loadSetting(T::class.java)
}
inline fun <reified T : Any> SettingCenter.checkSettingIsNull(): Boolean {
    return checkSettingIsNull(T::class.java)
}

inline fun <reified T : Any> SettingCenter.saveSetting(t: T) {
    return saveSetting(t,t::class.createType())
}