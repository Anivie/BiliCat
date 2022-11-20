@file:Suppress("NOTHING_TO_INLINE")

package ink.bluecloud.utils

import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.util.TypeUtils
import ink.bluecloud.exceptions.CodeException
import ink.bluecloud.exceptions.PojoException
import ink.bluecloud.model.data.Code

/**
 * 将 Any 对象转换为 关于JSON 的 POJO 对象
 * Any 对象的范围为（TypeUtils.cast 里面定义的对象）
 */
inline fun <T> to(clazz: Class<T>, obj: Any): T {
    var result: T? = null
    runCatching {
        result = if (obj is JSONObject)
            obj.to(clazz)
        else TypeUtils.cast(obj, clazz)
    }.onFailure { throw PojoException(obj.toString(), it) }

    return result ?: throw PojoException(obj.toString(), NullPointerException("result is null"))
}

inline fun <T> Any.toJson(clazz: Class<T>): T {
    return to(clazz, this.toString())
}

/**
 * 将字符串转换为 POJO对象，同时检测 Code 字段是否为 0
 */
inline fun <T> String.toObjJson(clazz: Class<T>): T {
    to(Code::class.java, this).let {
        if (it.code != 0) throw CodeException(it.code, it.message)
    }

    return to(clazz, this)
}