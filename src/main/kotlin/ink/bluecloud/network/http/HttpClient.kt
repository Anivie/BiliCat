package ink.bluecloud.network.http

import ink.bluecloud.model.networkapi.api.NetWorkResourcesProviderImpl
import okhttp3.Call
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.Response
import okio.IOException
import java.io.Closeable

/**
 * Http工具类，提供了post和get两种方法的请求
 * */
abstract class HttpClient : Client(),Closeable {
    override val netResourcesProvider = NetWorkResourcesProviderImpl()

    protected val defaultOnFailure: Call.(IOException) -> Unit by lazy {
        {
            it.printStackTrace()
        }
    }

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
}