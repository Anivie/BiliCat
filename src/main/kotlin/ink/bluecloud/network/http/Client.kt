package ink.bluecloud.network.http

import ink.bluecloud.model.networkapi.api.NetWorkResourcesProvider
import okhttp3.Headers

abstract class Client {
    abstract val netResourcesProvider: NetWorkResourcesProvider

    protected val defaultHeader = Headers.Builder()
        .add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36")
        .add("referer", "https://www.bilibili.com")
        .build()
}