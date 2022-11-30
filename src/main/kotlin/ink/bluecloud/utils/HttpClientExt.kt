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

suspend inline fun HttpClient.getForHead(url: HttpUrl): Boolean = suspendCoroutine { coroutine ->
    headFor(url) {
        coroutine.resume(code == 200)
    }
}

suspend inline fun HttpClient.getForBytes(url: HttpUrl): ByteArray = suspendCoroutine { coroutine ->
    getFor(url) {
        coroutine.resume(body.bytes())
    }
}