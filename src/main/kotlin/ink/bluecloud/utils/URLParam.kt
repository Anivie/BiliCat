package ink.bluecloud.utils

import okhttp3.HttpUrl

inline fun HttpUrl.param(params: String = "", handle: (HashMap<String, String>) -> Unit): HttpUrl {
    val builder = StringBuilder().append("?").append("${params}&")
    HashMap<String, String>().apply(handle)
        .forEach { builder.append(it.key).append("=").append(it.value).append("&") }
    return newBuilder(builder.deleteCharAt(builder.length - 1).toString())?.build()?: this
}