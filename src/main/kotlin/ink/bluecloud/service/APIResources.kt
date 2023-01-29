package ink.bluecloud.service

import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.network.http.CookieManager
import okhttp3.HttpUrl

abstract class APIResources : ClientService() {
    protected val API = netWorkResourcesProvider.api

    protected fun getCsrf(): String = httpClient.getCookieStore().toCookies().csrf
    protected fun getSession(): String = httpClient.getCookieStore().toCookies().session
    protected fun getRefreshToken(): String = httpClient.getCookieStore().toCookies().refreshToken
    protected open fun getCookieStore(): CookieManager = httpClient.getCookieStore()
    protected fun getCookie(): CookieJson = getCookieStore().toCookies()

    /**
     * 构建参数
     */
    protected inline fun buildParam(handle: (HashMap<String, String>) -> Unit) = HashMap<String, String>().apply(handle)

    /**
     * 构建URL请求
     */
    protected inline fun HttpUrl.param(params: String = "", handle: (HashMap<String, String>) -> Unit): HttpUrl {
        val builder = StringBuilder().append("?")
        if (params.isNotEmpty()) builder.append("${params}&")
        HashMap<String, String>().apply(handle)
            .forEach { builder.append(it.key).append("=").append(it.value).append("&") }
        return newBuilder(builder.deleteCharAt(builder.length - 1).toString())?.build() ?: this
    }



    /**
     * 构建API
     * @param url 网络资源路径
     * @param level Get请求或者Post请求，默认Get请求
     * @param apiName 当前访问的URL的名称，默认为类名称
     * @param getParams get请求默认参数
     * @param handle 请求参数
     */
    protected fun api(
        url: HttpUrl,
        level: APILevel = APILevel.Get,
        apiName: String = this.javaClass.name.substring(this.javaClass.name.lastIndexOf(".") + 1),
        getParams:String = "",
        handle: (HashMap<String, String>) -> Unit,
    ): Path {
        val params = HashMap<String, String>().apply(handle)
        logger.info(
            "API $level $apiName -> " +
                    "${if (level == APILevel.Get) url.param(getParams,handle = handle) else url}" +
                    " ${if (params.size > 0 && level != APILevel.Get) params else ""}"
        )

        return if (level == APILevel.Get)
            Path(level, url.param(getParams,handle = handle), params, apiName)
        else
            Path(level, url, params, apiName)

    }

    protected fun api(
        url: HttpUrl,
        level: APILevel = APILevel.Get,
        apiName: String = this.javaClass.name.substring(this.javaClass.name.lastIndexOf(".") + 1),
    ): Path {
        logger.info("API $level $apiName -> $url")
        return Path(level, url, HashMap<String, String>(), apiName)
    }

    protected data class Path(
        val method: APILevel,
        val url: HttpUrl,
        val params: HashMap<String, String>,
        val name: String,
    ){
        override fun toString(): String {
            return url.toString()
        }
    }

    protected enum class APILevel {
        Post,
        Get
    }
}