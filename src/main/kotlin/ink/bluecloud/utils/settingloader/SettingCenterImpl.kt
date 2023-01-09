@file:Suppress("OPT_IN_USAGE", "UNCHECKED_CAST")
@file:OptIn(InternalSerializationApi::class)

package ink.bluecloud.utils.settingloader

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
    private val setting = HashMap<String, Any>()

    override fun <T : Any> loadSetting(clazz: Class<T>): T? {
        return configPath[clazz]?.run {
            if (exists()) {
                ProtoBuf.decodeFromByteArray(clazz.kotlin.serializer(), Files.readAllBytes(this))
            } else null
        }
    }

    override fun <T : Any> chaekSettingIsNull(clazz: Class<T>) = loadSetting(clazz) == null

    override fun <T: Any> saveSetting(t: T, clazz: KType) {
        setting[clazz.javaClass.simpleName]?.run {
            setting[clazz.javaClass.simpleName] = t
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
inline fun <reified T : Any> SettingCenter.chaekSettingIsNull(): Boolean {
    return chaekSettingIsNull(T::class.java)
}

inline fun <reified T : Any> SettingCenter.saveSetting(t: T) {
    return saveSetting(t,t::class.createType())
}