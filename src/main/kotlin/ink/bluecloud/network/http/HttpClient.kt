package ink.bluecloud.network.http

import ink.bluecloud.model.networkapi.api.NetWorkResourcesProviderImpl
import okhttp3.*
import okio.IOException
import java.time.Duration

/**
 * Http工具类，提供了post和get两种方法的请求
 * */
abstract class HttpClient : Client() {
    //创建一个带有Cookie管理器的okhttp实例
    val okHttpClient = OkHttpClient.Builder().apply {
        cookieJar(CookieManager())
        connectTimeout(Duration.ofSeconds(10))
    }.build()
    override val netResourcesProvider = NetWorkResourcesProviderImpl()

    abstract fun getCookieStore(): CookieManager

    abstract fun getFor(
        url: HttpUrl,
        headers: Headers? = defaultHeader,
        onFailure: (Call.(IOException) -> Unit)? = null,
        onResponse: Response.(Call) -> Unit
    )

    abstract fun postFor(
        url: HttpUrl,
        params: Map<String, String>,
        headers: Headers? = defaultHeader,
        onFailure: (Call.(IOException) -> Unit)? = null,
        onResponse: Response.(Call) -> Unit
    )

    protected val defaultOnFailure: Call.(IOException) -> Unit by lazy {
        {
            it.printStackTrace()
        }
    }
}