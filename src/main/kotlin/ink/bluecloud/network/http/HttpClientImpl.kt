package ink.bluecloud.network.http

import okhttp3.*
import okio.IOException
import org.koin.core.annotation.Single

@Single
class HttpClientImpl: HttpClient() {
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

}