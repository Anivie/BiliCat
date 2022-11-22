package ink.bluecloud.network.http

import okhttp3.*
import okio.IOException
import org.koin.core.annotation.Single
import java.time.Duration

@Single
class HttpClientImpl: HttpClient() {

    //创建一个带有Cookie管理器的okhttp实例
    private val okHttpClient = OkHttpClient.Builder().apply {
        cookieJar(CookieManager())
        connectTimeout(Duration.ofSeconds(10))
    }.build()

    override fun getCookieStore(): CookieManager {
        return this.okHttpClient.cookieJar as CookieManager
    }

    override fun getFor(
        url: HttpUrl,
        headers: Headers?,
        onFailure: (Call.(IOException) -> Unit)?,
        onResponse: Response.(Call) -> Unit
    ) {
        val request = headers?.run {
            Request(url, headers)
        }?: Request(url)

        executeRequest(request, onFailure, onResponse)
    }

    override fun headFor(
        url: HttpUrl,
        headers: Headers?,
        onFailure: (Call.(IOException) -> Unit)?,
        onResponse: Response.(Call) -> Unit,
    ) {
        executeRequest(Request.Builder().apply {
            headers?.run { headers(this) }
            url(url)
            head()
        }.build(), onFailure, onResponse)
    }

    override fun postFor(
        url: HttpUrl,
        params: Map<String, String>,
        headers: Headers?,
        onFailure: (Call.(IOException) -> Unit)?,
        onResponse: Response.(Call) -> Unit
    ) {
        val requestBody = FormBody.Builder().apply {
            params.forEach { (k, v) ->
                add(k,v)
            }
        }.build()

        executeRequest(Request.Builder().apply {
            headers?.run { headers(this) }
            url(url)
            post(requestBody)
        }.build(), onFailure, onResponse)
    }

    private fun executeRequest(
        request: Request,
        onFailure: (Call.(IOException) -> Unit)?,
        onResponse: Response.(Call) -> Unit
    ) {
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFailure?.run {
                    call.onFailure(e)
                } ?: call.defaultOnFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.onResponse(call)
            }
        })
    }

    override fun close() {
        okHttpClient.dispatcher.executorService.shutdown()
    }
}