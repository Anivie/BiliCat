package ink.bluecloud.utils

import ink.bluecloud.network.http.HttpClient
import okhttp3.HttpUrl
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend inline fun HttpClient.getForString(url: HttpUrl): String = suspendCoroutine { coroutine ->
    getFor(url) {
        coroutine.resume(body.string())
    }
}