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
import kotlin.io.path.notExists
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.jvm.jvmErasure

@Single
class SettingCenterImpl : SettingCenter() {
    private val setting = HashMap<String, Any>()

    override fun readAllSetting() {
        configPath.forEach {
            if (it.value.notExists()) return@forEach

            setting[it.key.simpleName ?: "匿名类-${it.key}"] = ProtoBuf.decodeFromByteArray(it.key.kotlin.serializer(), Files.readAllBytes(it.value))
        }
    }

    override fun <T : Any> readSettingOnly(clazz: Class<T>): T? {
        return configPath[clazz]?.run {
            if (exists()) {
                ProtoBuf.decodeFromByteArray(clazz.kotlin.serializer(), Files.readAllBytes(this))
            } else null
        }
    }

    override fun <T> getSetting(clazz: Class<T>): T? {
        return setting[clazz.simpleName] as? T
    }

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

inline fun <reified T : Any> SettingCenter.readSettingOnly(): T? {
    return readSettingOnly(T::class.java)
}

inline fun <reified T : Any> SettingCenter.saveSetting(t: T) {
    return saveSetting(t,t::class.createType())
}